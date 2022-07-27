package com.assist.imobilandroidapp.screens.favorites

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.FavoritesListingAdapter
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.ListingFavoritesFromDB
import com.assist.imobilandroidapp.databinding.ActivityFavoritesBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FavoritesActivity : AppCompatActivity(), FavoritesListingAdapter.OnFavIconClick {
    private lateinit var binding: ActivityFavoritesBinding
    private var userType: Int? = 0
    var foundListings: ArrayList<ListingFavoritesFromDB> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoritesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getListings()
        onProfileIconClick()
        onSearchIconClick()
    }

    private fun initRV() {
        val listingsRecyclerView: RecyclerView = binding.rvListings
        val listingItemsAdapter = FavoritesListingAdapter(foundListings, this)
        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = GridLayoutManager(applicationContext, 2)
        userType?.let { listingItemsAdapter.setUserType(it) }
    }

    private fun getListings() {
        RetrofitClient.instance.getFavoritesListing(
            SharedPrefManager.getInstance().fetchUserId(),
            SharedPrefManager.getInstance().fetchToken()
        )
            .enqueue(object : Callback<List<ListingFavoritesFromDB>> {
                override fun onResponse(
                    call: Call<List<ListingFavoritesFromDB>>,
                    response: Response<List<ListingFavoritesFromDB>>
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
                                foundListings.addAll(response.body()!!)
                                initRV()
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

                override fun onFailure(
                    call: Call<List<ListingFavoritesFromDB>>,
                    t: Throwable
                ) {
                    Toast.makeText(
                        applicationContext,
                        t.message,
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }

    override fun onFavIconClick(listingFavoritesFromDB: ListingFavoritesFromDB) {
        SharedPrefManager.getInstance().fetchUserId()?.let {
            RetrofitClient.instance.addToFavoritesList(it, listingFavoritesFromDB.id)
                .enqueue(object : Callback<String> {
                    override fun onResponse(
                        call: Call<String>,
                        response: Response<String>
                    ) {
                        when (response.code()) {
                            400, 401 -> {
                                Toast.makeText(
                                    this@FavoritesActivity,
                                    getText(R.string.something_wrong).toString() + "400",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                            200 -> {
                                if (response.body()?.isNotEmpty() == true) {
                                    Toast.makeText(
                                        this@FavoritesActivity,
                                        getText(R.string.success).toString() + "400",
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@FavoritesActivity,
                                        getText(R.string.nothing_found).toString(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<String>, t: Throwable) {
                        Toast.makeText(
                            this@FavoritesActivity,
                            t.message,
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }


    }

    override fun onListingClick(listingFavoritesFromDB: ListingFavoritesFromDB) {
        val intent = Intent(this, ListingScreenActivity::class.java)
        startActivity(intent)
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            if (userType == StartFragment.UserTypeConstants.LOGGED_IN_USER) {
                intent = Intent(this, MainProfileActivity::class.java)
                startActivity(intent)
            } else {
                //LoginDialogFragment().show(supportFragmentManager, LoginDialogFragment.TAG)
                intent = Intent(this, MainProfileActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun onSearchIconClick() {
    }
}