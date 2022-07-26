package com.assist.imobilandroidapp.screens.add

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.PostListing
import com.assist.imobilandroidapp.databinding.ActivityAddListingBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity
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

class AddListingActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityAddListingBinding
    private var listingCategory: Int = 0
    private var listingImages: ArrayList<String> = ArrayList()  // We'll send this to the DB
    private var listingImagesPreview: ArrayList<Uri> =
        ArrayList()  // We'll send this to the Listing Preview

    private var searchQuery: String = ""
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSpinner()
        addPhotos()
        onProfileIconClick()
        onSearchIconClick()
        onPreviewBtnClick()
        onPublishBtnClick()
    }

    private fun allFieldsValidated(): Boolean {
        return !(!validateTitle() or !validateLocation() or !validatePrice() or !validateDescription() or !validatePhoneNumber() or !validatePhotos())
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
                        SharedPrefManager.getInstance().fetchUserId().toString(),
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
                                val intent = Intent(
                                    this@AddListingActivity,
                                    ListingConfirmedActivity::class.java
                                )
                                startActivity(intent)
                            }

                            else -> {
                                Toast.makeText(
                                    applicationContext,
                                    getText(R.string.something_wrong).toString(),
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(
                            applicationContext,
                            t.message,
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

    private fun validatePhotos(): Boolean {
        return if (listingImages.size == 0) {
            binding.tvAddPhotosHelper.setTextColor(resources.getColor(R.color.red_400))
            false
        } else {
            binding.tvAddPhotosHelper.setTextColor(resources.getColor(R.color.gray_400))
            true
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

    private fun onPreviewBtnClick() {
        binding.btnPreview.setOnClickListener {
            if (allFieldsValidated()) {
                val intent = Intent(this, PreviewListingActivity::class.java)
                intent.putExtra("title", binding.etListingTitle.text.toString())
                intent.putExtra("description", binding.etListingDescription.text.toString())
                intent.putExtra("location", binding.etListingLocation.text.toString())
                intent.putExtra("price", binding.etListingPrice.text.toString())
                intent.putExtra("priceDouble", binding.etListingPrice.text.toString().toDouble())
                intent.putExtra("images", listingImagesPreview)
                intent.putExtra("category", listingCategory)
                intent.putExtra("phoneNumber", binding.etPhoneNumber.text.toString())
                startActivity(intent)
            }
        }
    }

    private lateinit var clickedButton: ImageView
    private var position = 0
    private fun addPhotos() {
        binding.btn1.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn1
                position = 0
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn2.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn2
                position = 1
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn3.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn3
                position = 2
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn4.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn4
                position = 3
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn5.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn5
                position = 4
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn6.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn6
                position = 5
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn7.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn7
                position = 6
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn8.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn8
                position = 7
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }

        binding.btn9.setOnClickListener {
            if (listingImages.size < 9) {
                clickedButton = binding.btn9
                position = 8
                imageChooser()
            } else {
                Toast.makeText(this, R.string.only_9_photos, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun imageChooser() {
        val intent = Intent(
            Intent.ACTION_PICK,
            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        )
        startActivityForResult(intent, 100)
    }

    private var sImage: String = ""
    private var uri: Uri = Uri.EMPTY
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {
            uri = data.data!!
            if (position in listingImagesPreview.indices) {
                listingImagesPreview[position] = uri
            } else {
                listingImagesPreview.add(uri)
            }
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            sImage = java.util.Base64.getEncoder().encodeToString(bytes)
            if (position in listingImages.indices) {
                listingImages[position] = sImage
            } else {
                listingImages.add(sImage)
            }

            Glide.with(applicationContext).load(uri).override(345, 240).transform(
                MultiTransformation(CenterCrop(), GranularRoundedCorners(12f, 12f, 12f, 12f))
            ).error(R.drawable.photo_replacement_1).into(clickedButton)
        }
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onSearchIconClick() {
        binding.toolbar.ivSearchIcon.setOnClickListener {
            binding.svSearch.isVisible = true
            binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(text: String): Boolean {
                    searchQuery = text
                    binding.svSearch.isGone = true
                    val intent = Intent(this@AddListingActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }
}