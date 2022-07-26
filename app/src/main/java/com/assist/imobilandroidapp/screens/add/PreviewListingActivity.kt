package com.assist.imobilandroidapp.screens.add

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.SearchView
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.PostListing
import com.assist.imobilandroidapp.databinding.ActivityPreviewListingBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.listing.ViewImagesActivity
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import kotlin.collections.ArrayList

class PreviewListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewListingBinding
    private var listingTitle: String = ""
    private var listingDescription: String = ""
    private var listingLocation: String = ""
    private var listingImages: ArrayList<Uri> = ArrayList()
    private var listingImagesToDB: ArrayList<String> = ArrayList()
    private var phoneNumber: String = ""
    private var priceDouble: Double = 0.0
    private var searchQuery: String = ""
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getData()
        onEditBtnClick()
        onPublishBtnClick()
        onSeeAllPhotosBtnClick()
        onSearchIconClick()
        onProfileIconClick()
        encodeUri()
    }

    private fun getData() {
        val completePrice = intent.extras?.getString("price").toString() + " lei"

        binding.tvTitleEstate.text = intent.extras?.getString("title").toString()
        binding.tvDescriptionText.text = intent.extras?.getString("description").toString()
        binding.tvLocationText.text = intent.extras?.getString("location").toString()
        binding.tvPriceEstate.text = completePrice
        priceDouble = intent.extras?.getDouble("priceDouble")!!
        listingImages = intent.extras?.getParcelableArrayList<Uri>("images") as ArrayList<Uri>

        Glide.with(applicationContext).load(listingImages[0]).transform(
            MultiTransformation(CenterCrop(), GranularRoundedCorners(12f, 12f, 12f, 12f))
        ).error(R.drawable.photo_replacement_1).into(binding.ivMainPicture)

    }

    private fun onEditBtnClick() {
        binding.btnEdit.setOnClickListener {
            finish()
        }
    }

    private fun onPublishBtnClick() {
        binding.btnPublish.setOnClickListener {
            RetrofitClient.instance.addListing(
                PostListing(
                    listingTitle,
                    listingDescription,
                    listingLocation,
                    priceDouble,
                    listingImagesToDB,
                    intent.extras?.getInt("category")!!,
                    SharedPrefManager.getInstance().fetchUserId().toString(),
                    true,
                    phoneNumber
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
                            val intent = Intent(this@PreviewListingActivity, ListingConfirmedActivity::class.java)
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
                        getText(R.string.something_wrong).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
        }
    }

    private fun onSeeAllPhotosBtnClick() {
        binding.ivMorePictureButton.setOnClickListener {
            val intent = Intent(this, ViewImagesActivity::class.java)
            intent.putExtra("images", listingImages)
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
                    val intent = Intent(this@PreviewListingActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun encodeUri() {
        var sImage: String
        for(item in listingImages) {
            val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, item)
            val stream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
            val bytes = stream.toByteArray()
            sImage = java.util.Base64.getEncoder().encodeToString(bytes)
            listingImagesToDB.add(sImage)
        }
    }
}