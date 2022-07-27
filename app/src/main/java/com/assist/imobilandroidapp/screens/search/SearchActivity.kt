package com.assist.imobilandroidapp.screens.search

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ListingItemWithDescAdapter
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.assist.imobilandroidapp.databinding.ActivitySearchBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.LoginDialogFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.profile.MessagesActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchActivity : AppCompatActivity(), ListingItemWithDescAdapter.OnFavIconClick {

    private lateinit var binding: ActivitySearchBinding
    private var searchQuery: String? = ""
    private var userType: Int? = 0

    val foundListings: ArrayList<ListingFromDBObject> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        searchQuery = bundle?.getString("searchQuery")
        userType = bundle?.getInt("userType")

        getListings()
        editSearchText()
        onProfileIconClick()
        onSearchIconClick()
        onMessageClick()
        onToolbarFavIconClick()
    }

    private fun editSearchText() {
        var newText = ""
        newText = if(foundListings.size != 0) {
            foundListings.size.toString() + " " +
                    getText(R.string.search_results_placeholder_activity).toString() + " " + searchQuery
        } else {
            getText(R.string.search_results_placeholder_activity).toString() + " " + searchQuery
        }
        binding.tvTitle.text = newText
    }

    private fun initRV() {
        val listingsRecyclerView: RecyclerView = binding.rvListings
        val listingItemsAdapter = ListingItemWithDescAdapter(foundListings, this)

        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        userType?.let { listingItemsAdapter.setUserType(it) }
    }

    private fun getListings() {
        searchQuery.let { query ->
            if (query != null) {
                RetrofitClient.instance.searchForListing(query)
                    .enqueue(object : Callback<List<ListingFromDBObject>> {
                        override fun onResponse(
                            call: Call<List<ListingFromDBObject>>,
                            response: Response<List<ListingFromDBObject>>
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
                                        editSearchText()
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
                            call: Call<List<ListingFromDBObject>>,
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
        }
    }

    override fun onFavIconClick(listingItemWithDesc: ListingFromDBObject) {
        SharedPrefManager.getInstance().fetchUserId()?.let {
            RetrofitClient.instance.addToFavoritesList(it, listingItemWithDesc.id)
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

    override fun onListingClick(ListingItemWithDesc: ListingFromDBObject) {
        val intent = Intent(this, ListingScreenActivity::class.java)
        intent.putExtra("userType", userType)
        intent.putExtra("id", ListingItemWithDesc.id)
        startActivity(intent)
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
           if(userType == StartFragment.UserTypeConstants.LOGGED_IN_USER) {
               intent = Intent(this, MainProfileActivity::class.java)
               startActivity(intent)
           }
            else {
               LoginDialogFragment().show(supportFragmentManager, LoginDialogFragment.TAG)
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
                    val intent = Intent(this@SearchActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }

    private fun onMessageClick() {
        binding.fab.setOnClickListener {
            val intent = Intent(applicationContext, MessagesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onToolbarFavIconClick() {
        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            val intent = Intent(applicationContext, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}