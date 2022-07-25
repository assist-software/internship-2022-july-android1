package com.assist.imobilandroidapp.screens.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.PostListing
import com.assist.imobilandroidapp.databinding.ActivityPreviewListingBinding
import com.assist.imobilandroidapp.screens.client.main.ClientActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.collections.ArrayList

class PreviewListingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPreviewListingBinding
    private var listingTitle: String = ""
    private var listingDescription: String = ""
    private var listingLocation: String = ""
    private var listingPrice: String = ""
    private var listingImages: ArrayList<String> = ArrayList()
    private var listingCategory: String = ""
    private var phoneNumber: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreviewListingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent = Intent(this, ClientActivity::class.java)

        getData()
        addData()
        onEditBtnClick()
        onPublishBtnClick()
    }

    private fun getData() {
        val bundle = intent.extras
        listingTitle = bundle?.getString("title").toString()
        listingDescription = bundle?.getString("description").toString()
        listingLocation = bundle?.getString("location").toString()
        listingPrice = bundle?.getString("price").toString() + " " + "lei"
        listingImages = bundle?.getStringArrayList("images")!!
        listingCategory = bundle?.getInt("category").toString()
        phoneNumber = bundle?.getString("phoneNumber").toString()
    }

    private fun addData() {
        binding.tvTitleEstate.text = listingTitle
        binding.tvDescriptionText.text = listingDescription
        binding.tvLocationText.text = listingLocation
        binding.tvPriceEstate.text = listingPrice
        binding.ivMainPicture.setImageResource(R.drawable.photo_replacement_2)
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
                    listingPrice.toDouble(),
                    arrayListOf(),
                    listingCategory.toInt(),
                    SharedPrefManager.getInstance().fetchUserID().toString(),
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
}