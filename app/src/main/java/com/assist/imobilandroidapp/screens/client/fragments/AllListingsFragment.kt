package com.assist.imobilandroidapp.screens.client.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.CarouselItemAdapter
import com.assist.imobilandroidapp.adapters.ListingItemAdapter
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.assist.imobilandroidapp.databinding.FragmentAllListingsBinding
import com.assist.imobilandroidapp.items.CarouselItem
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.client.main.ListingSingleCategoryActivity
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AllListingsFragment : Fragment(), CarouselItemAdapter.OnItem, ListingItemAdapter.OnFavIconClickSmallRV {

    private var _binding: FragmentAllListingsBinding? = null
    private val binding get() = _binding!!
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER
    private var carouselItemAdapter: CarouselItemAdapter ?= null

    val bigHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val smallHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val foundListings: ArrayList<ListingFromDBObject> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllListingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       getListings()
    }

    private fun initRV() {
        val carouselRecyclerViewItem: RecyclerView = binding.rvParent
        val layoutManager = LinearLayoutManager(activity)
        carouselItemAdapter = CarouselItemAdapter(carouselItemList(), this, this)
        carouselItemAdapter!!.setUserType(userType)

        carouselRecyclerViewItem.adapter = carouselItemAdapter
        carouselRecyclerViewItem.layoutManager = layoutManager
    }

    private fun carouselItemList(): List<CarouselItem> {
        val categoryList = ArrayList<CarouselItem>()

        val latestListings = CarouselItem(getText(R.string.latest).toString(), foundListings)
        categoryList.add(latestListings)

        val bigHouse = CarouselItem(getText(R.string.big_house).toString(), bigHouseListings)
        categoryList.add(bigHouse)

        val smallHouse = CarouselItem(getText(R.string.small_house).toString(), smallHouseListings)
        categoryList.add(smallHouse)

        return categoryList
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
                            activity,
                            getText(R.string.something_wrong).toString(),
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
                                activity,
                                getText(R.string.nothing_found).toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<ListingFromDBObject>>, t: Throwable) {
                Toast.makeText(
                    activity,
                    t.message,
                    Toast.LENGTH_LONG
                ).show()
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(carouselItem: CarouselItem) {
        val intent = Intent(activity, ListingSingleCategoryActivity::class.java)
        intent.putExtra("title", carouselItem.carouselTitle)
        intent.putExtra("userType", userType)
        startActivity(intent)
    }

    override fun onFavIconClick(listingItem: ListingFromDBObject) {
        // Do nothing
    }

    override fun onListingItemClick(listingItem: ListingFromDBObject) {
        val intent = Intent(activity, ListingScreenActivity::class.java)
        val listingId = listingItem.id
        intent.putExtra("id", listingId)
        intent.putExtra("userType", userType)
        startActivity(intent)
    }
}
