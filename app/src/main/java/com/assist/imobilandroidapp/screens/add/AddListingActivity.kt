package com.assist.imobilandroidapp.screens.add

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.PostListing
import com.assist.imobilandroidapp.databinding.ActivityAddListingBinding
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.assist.imobilandroidapp.utils.Validator
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.util.Base64.getEncoder
import kotlin.collections.ArrayList

class AddListingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddListingBinding
    private var listingCategory: Int = 0
    private var listingImages: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()
        onPublishBtnClick()
        onPreviewBtnCLick()
        addPhotos()
    }

    private fun allFieldsValidated(): Boolean {
        return !(!validateTitle() or !validateLocation() or !validatePrice() or !validateDescription() or !validatePhoneNumber())
    }

    private fun onPublishBtnClick() {
        binding.btnPublish.setOnClickListener {
            if (allFieldsValidated()) {
                RetrofitClient.instance.addListing(
                    PostListing(
                        binding.etListingTitle.text.toString(),
                        binding.etListingDescription.text.toString(),
                        binding.etListingLocation.text.toString(),
                        binding.etListingPrice.text.toString().toDouble(),
                        listingImages,
                        listingCategory,
                        SharedPrefManager.getInstance().fetchUserID().toString(),
                        true,
                        binding.etPhoneNumber.text.toString()
                    ), SharedPrefManager.getInstance().fetchToken()
                ).enqueue(object : Callback<String> {
                    override fun onResponse(call: Call<String>, response: Response<String>) {
                        when (response.code()) {
                            200, 201 -> {
                                Toast.makeText(
                                    applicationContext,
                                    getText(R.string.success_create_listing).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            else -> {
                                Toast.makeText(
                                    applicationContext,
                                    getText(R.string.something_wrong).toString() + " Got Response",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            t.message + " It failed!",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
            } else {
                Toast.makeText(
                    applicationContext,
                    getText(R.string.something_wrong),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun validateTitle(): Boolean {
        val validate =
            Validator(binding.etListingTitle, binding.tvTitleError, applicationContext, resources)
        return validate.validateNewListingFields()
    }

    private fun validatePrice(): Boolean {
        val priceString = binding.etListingPrice.text.toString()
        val padding = binding.etListingPrice.paddingLeft

        if (priceString.isEmpty()) {
            binding.tvPriceError.isVisible = true
            binding.etListingPrice.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            binding.etListingPrice.setPadding(padding, padding, padding, padding)
            return false
        } else if (priceString[0] == '0') {
            binding.tvPriceError.isVisible = true
            binding.etListingPrice.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            binding.etListingPrice.setPadding(padding, padding, padding, padding)
            return false
        } else {
            binding.tvPriceError.isGone = true
            binding.etListingPrice.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            binding.etListingPrice.setPadding(padding, padding, padding, padding)
            return true
        }
    }

    private fun validateDescription(): Boolean {
        val validate = Validator(
            binding.etListingDescription,
            binding.tvDescriptionMessage,
            applicationContext,
            resources
        )
        return validate.validateListingDescription()
    }

    private fun validateLocation(): Boolean {
        val validate = Validator(
            binding.etListingLocation,
            binding.tvLocationError,
            applicationContext,
            resources
        )
        return validate.validateNewListingFields()
    }

    private fun validatePhoneNumber(): Boolean {
        val phoneNumber = binding.etPhoneNumber.text.toString()
        val padding = binding.etPhoneNumber.paddingLeft

        if (phoneNumber.isEmpty() or (phoneNumber.length != 10)) {
            binding.tvPhoneNumberError.isVisible = true
            binding.etPhoneNumber.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            binding.etPhoneNumber.setPadding(padding, padding, padding, padding)
            return false
        } else {
            for (char in phoneNumber) {
                if (!char.isDigit()) {
                    return false
                }
            }
            binding.tvPhoneNumberError.isGone = true
            binding.etPhoneNumber.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            binding.etPhoneNumber.setPadding(padding, padding, padding, padding)
            return true
        }
    }

    private fun initSpinner() {
        ArrayAdapter.createFromResource(
            this,
            R.array.spinner_categories,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.spinnerCategory.adapter = adapter
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, view: View?, pos: Int, id: Long) {
        val padding = binding.rlSpinner.paddingLeft

        when (pos) {
            0 -> listingCategory = 0
            1 -> listingCategory = 1
        }

        binding.rlSpinner.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
        binding.tvCategoryError.isGone = true
        binding.rlSpinner.setPadding(padding, padding, padding, padding)

    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
        val padding = binding.rlSpinner.paddingLeft

        binding.rlSpinner.background =
            ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
        binding.tvCategoryError.isVisible = true
        binding.rlSpinner.setPadding(padding, padding, padding, padding)
    }

    private fun onPreviewBtnCLick() {
        binding.btnPreview.setOnClickListener {
            if (allFieldsValidated()) {
                val intent = Intent(this, PreviewListingActivity::class.java)
                intent.putExtra("title", binding.etListingTitle.text.toString())
                intent.putExtra("description", binding.etListingDescription.text.toString())
                intent.putExtra("location", binding.etListingLocation.text.toString())
                intent.putExtra("price", binding.etListingPrice.text.toString())
                intent.putExtra("images", listingImages)
                intent.putExtra("category", listingCategory.toString())
                intent.putExtra("phoneNumber", binding.etPhoneNumber.text.toString())
                startActivity(intent)
            }
        }
    }

    private fun encodeImage(bm: Bitmap): String {
        val baos = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val bytes = baos.toByteArray()

        return Base64.encodeToString(bytes, Base64.DEFAULT)
    }

    private fun addPhotos() {
        binding.btn1.setOnClickListener {
            imageChooser()
        }
    }

    private fun imageChooser() {
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 100)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//
//        if (resultCode == RESULT_OK && (data != null)) run {
//            val selectedImage: Uri = data.data!!
//            val imageStream = contentResolver.openInputStream(selectedImage)
//            val bitmap = BitmapFactory.decodeStream(imageStream)
//            listingImages.add(encodeImage(bitmap))
//            println(listingImages)
//
//            Glide.with(applicationContext).load(selectedImage).override(345, 240).transform(
//                MultiTransformation(CenterCrop(), GranularRoundedCorners(12f, 12f, 12f, 12f))
//            ).error(R.drawable.photo_replacement_1).into(binding.btn1)
//        }
//    }

        private var sImage: String = ""
//    private fun imageChooser() {
//        val intent = Intent(Intent.ACTION_VIEW)
//        intent.type = "image/*"
//        startActivityForResult(Intent.createChooser(intent, "Select Image"), 100)
//    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if(requestCode == 100 && resultCode == RESULT_OK && data != null) {
            val uri = data.data
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            sImage = java.util.Base64.getEncoder().encodeToString(bytes)
            listingImages.add(sImage)
            println(listingImages[0])
            Glide.with(applicationContext).load(uri).override(345, 240).transform(
                MultiTransformation(CenterCrop(), GranularRoundedCorners(12f, 12f, 12f, 12f))
           ).error(R.drawable.photo_replacement_1).into(binding.btn1)
        }
    }
}