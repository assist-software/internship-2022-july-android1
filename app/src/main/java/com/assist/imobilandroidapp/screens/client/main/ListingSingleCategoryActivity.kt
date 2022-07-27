package com.assist.imobilandroidapp.screens.client.main

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
import com.assist.imobilandroidapp.databinding.ActivityClientBinding
import com.assist.imobilandroidapp.databinding.ActivityListingSingleCategoryBinding
import com.assist.imobilandroidapp.screens.add.AddListingActivity
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ListingSingleCategoryActivity : AppCompatActivity(), ListingItemWithDescAdapter.OnFavIconClick {

    private lateinit var binding: ActivityListingSingleCategoryBinding
    private var searchQuery = ""
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER
    private var categoryTitle: String? = ""

    val bigHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val smallHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val foundListings: ArrayList<ListingFromDBObject> = arrayListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListingSingleCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras
        categoryTitle = bundle?.getString("title")
        binding.tvTitle.text = categoryTitle

        getListings()
        onSearchIconClick()
        onProfileIconClick()
        onAddFABClick()
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
                    val intent =
                        Intent(this@ListingSingleCategoryActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }

    private fun initRV() {
        val listingsRecyclerView: RecyclerView = binding.rvListings
        var listingItemsAdapter = ListingItemWithDescAdapter(emptyList(), this)

        when (categoryTitle) {
            getText(R.string.latest).toString() -> listingItemsAdapter =
                ListingItemWithDescAdapter(foundListings, this)

            getText(R.string.big_house).toString() -> listingItemsAdapter =
                ListingItemWithDescAdapter(bigHouseListings, this)

            getText(R.string.small_house).toString() -> listingItemsAdapter =
                ListingItemWithDescAdapter(smallHouseListings, this)
        }

        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = LinearLayoutManager(applicationContext)
        listingItemsAdapter.setUserType(userType)
    }

    private fun getListings() {
        RetrofitClient.instance.getListings().enqueue(object : Callback<List<ListingFromDBObject>> {
            override fun onResponse(
                call: Call<List<ListingFromDBObject>>,
                response: Response<List<ListingFromDBObject>>
            ) {
                when (response.code()) {
                    400, 401 -> {
                        Toast.makeText(
                           applicationContext,
                            getText(R.string.something_wrong).toString() + "400",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                    200 -> {
                        if (response.body()?.isNotEmpty() == true) {
                            foundListings.addAll(response.body()!!)
                            for (item in foundListings) {
                                when(item.category) {
                                    0 -> bigHouseListings.add(item)
                                    1 -> smallHouseListings.add(item)
                                }
                            }
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

            override fun onFailure(call: Call<List<ListingFromDBObject>>, t: Throwable) {
                Toast.makeText(
                    applicationContext,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onFavIconClick(listingItemWithDesc: ListingFromDBObject) {
        // Do nothing
    }

    override fun onListingClick(ListingItemWithDesc: ListingFromDBObject) {
        val intent = Intent(this, ListingScreenActivity::class.java)
        val listingId = ListingItemWithDesc.id
        println(listingId)
        intent.putExtra("id", listingId)
        intent.putExtra("userType", userType)
        startActivity(intent)
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onAddFABClick() {
        binding.fabAddListing.setOnClickListener {
            intent = Intent(this, AddListingActivity::class.java)
            startActivity(intent)
        }
    }
}