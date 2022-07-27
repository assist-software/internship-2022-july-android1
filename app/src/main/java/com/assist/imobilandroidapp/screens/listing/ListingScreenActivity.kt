package com.assist.imobilandroidapp.screens.listing

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.SingleListingResponse
import com.assist.imobilandroidapp.databinding.ActivityListingScreenBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.FavouritesDialogFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.LoginDialogFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity
import com.assist.imobilandroidapp.screens.favorites.FavoritesDialogFragment
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.profile.MessagesActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListingScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListingScreenBinding
    private var listingId: String = ""
    private var listingImages: ArrayList<String> = ArrayList()
    private var userType = 0
    private var authorId: String = ""
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListingScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initButtons()
        onFavIconClick()
        getListingId()
        getUserType()
        getListingData()
        onMessageClick()
        onToolbarFavIconClick()
        onSearchIconClick()
        profilePicButton()
    }

    private fun getListingData() {
        RetrofitClient.instance.viewSingleListing(listingId)
            .enqueue(object : Callback<SingleListingResponse> {
                override fun onResponse(
                    call: Call<SingleListingResponse>,
                    response: Response<SingleListingResponse>
                ) {
                    when (response.code()) {
                        200, 201 -> {
                            response.body()?.let {
                                insertListingData(
                                    it.images,
                                    it.title,
                                    it.price,
                                    it.description,
                                    it.location
                                )
                                listingImages = it.images
                                authorId = it.authorId
                            }
                        }
                        else -> {
                            Toast.makeText(
                                applicationContext,
                                R.string.something_wrong.toString() + " " + response.errorBody(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<SingleListingResponse>, t: Throwable) {
                    Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG)
                        .show()
                }
            })
    }

    private fun getAuthorData() {
        // There is no API call for this, yet
    }

    private fun onFavIconClick() {
        binding.btnHeart.setOnClickListener {
            when (userType) {
                StartFragment.UserTypeConstants.GUEST -> FavouritesDialogFragment().show(
                    supportFragmentManager,
                    FavouritesDialogFragment.TAG
                )
                StartFragment.UserTypeConstants.LOGGED_IN_USER -> {
                    SharedPrefManager.getInstance().fetchUserId()?.let {
                        RetrofitClient.instance.addToFavoritesList(it, listingId)
                            .enqueue(object : Callback<String> {
                                override fun onResponse(
                                    call: Call<String>,
                                    response: Response<String>
                                ) {
                                    when (response.code()) {
                                        400, 401 -> {
                                            Toast.makeText(
                                                applicationContext,
                                                getText(R.string.something_wrong).toString(),
                                                Toast.LENGTH_LONG
                                            ).show()
                                        }
                                        200 -> {
                                            if (response.body()?.isNotEmpty() == true) {
                                                Toast.makeText(
                                                    applicationContext,
                                                    getText(R.string.added_to_fav).toString(),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            } else {
                                                Toast.makeText(
                                                    applicationContext,
                                                    getText(R.string.nothing_found).toString(),
                                                    Toast.LENGTH_LONG
                                                ).show()
                                            }
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
                    }
                }
            }
        }
    }

    private fun insertListingData(
        images: ArrayList<String>,
        title: String,
        price: Double,
        description: String,
        location: String
    ) {
        Glide.with(applicationContext).load(images[0]).transform(
            MultiTransformation(CenterCrop(), GranularRoundedCorners(12f, 12f, 12f, 12f))
        ).error(R.drawable.photo_replacement_1).into(binding.ivMainPicture)
        binding.tvTitleEstate.text = title
        val priceString = "$price lei"
        binding.tvPriceEstate.text = priceString
        binding.tvDescriptionText.text = description
        binding.tvLocationText.text = location
    }

    private fun getListingId() {
        listingId = intent.extras?.getString("id").toString()
        println(listingId)
    }

    private fun getUserType() {
        userType = intent.extras!!.getInt("userType")
    }

    private fun initButtons() {
        binding.ivMorePictureButton.setOnClickListener {
            morePictureButton()
        }
        binding.btnShare.setOnClickListener {
            shareButton()
        }
        binding.btnPurchase.setOnClickListener {
            purchaseButton()
        }
        binding.btnContactSeller.setOnClickListener {
            contactSellerButton()
        }
    }

    private fun morePictureButton() {
        val intent = Intent(this, ViewImagesActivity::class.java)
        intent.putExtra("imagesURL", listingImages)
        this.startActivity(intent)
    }

    private fun profilePicButton() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            if (userType == StartFragment.UserTypeConstants.LOGGED_IN_USER) {
                val intent = Intent(this, MainProfileActivity::class.java)
                this.startActivity(intent)
            } else {
                LoginDialogFragment().show(supportFragmentManager, LoginDialogFragment.TAG)
            }
        }
    }

    private fun contactSellerButton() {
        Toast.makeText(this, getString(R.string.contact_seller), Toast.LENGTH_SHORT).show()
    }

    private fun purchaseButton() {
        Toast.makeText(this, getString(R.string.purchase), Toast.LENGTH_SHORT).show()
    }

    private fun shareButton() {
        Toast.makeText(this, getString(R.string.share), Toast.LENGTH_SHORT).show()
    }

    private fun onMessageClick() {
        binding.fab.setOnClickListener {
            val intent = Intent(applicationContext, MessagesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onToolbarFavIconClick() {
        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            if (userType == StartFragment.UserTypeConstants.LOGGED_IN_USER) {
                val intent = Intent(applicationContext, FavoritesActivity::class.java)
                startActivity(intent)
            } else {
                FavoritesDialogFragment().show(supportFragmentManager, LoginDialogFragment.TAG)
            }
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
                    val intent = Intent(applicationContext, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }
}
