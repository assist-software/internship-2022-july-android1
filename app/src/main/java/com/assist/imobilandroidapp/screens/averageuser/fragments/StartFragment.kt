package com.assist.imobilandroidapp.screens.averageuser.fragments

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
import com.assist.imobilandroidapp.databinding.FragmentStartBinding
import com.assist.imobilandroidapp.items.CarouselItem
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StartFragment : Fragment(), CarouselItemAdapter.OnItem, ListingItemAdapter.OnFavIconClickSmallRV{

    private var _binding: FragmentStartBinding? = null
    private val binding get() = _binding!!
    private var carouselItemAdapter: CarouselItemAdapter ?= null
    private var userType: Int = UserTypeConstants.GUEST

    val bigHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val smallHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val foundListings: ArrayList<ListingFromDBObject> = arrayListOf()

    object UserTypeConstants {
        const val GUEST = 0
        const val LOGGED_IN_USER = 1
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentStartBinding.inflate(inflater, container, false)
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

    override fun onItemClick(carouselItem: CarouselItem) {
        val bundle = Bundle()
        bundle.putString("title", carouselItem.carouselTitle)

        val fragment = LatestFragment()
        fragment.arguments = bundle
        changeFragment(fragment)
    }

    override fun onFavIconClick(listingItem: ListingFromDBObject) {
        val fragment = FavouritesEmptyFragment()
        changeFragment(fragment)
    }

    override fun onListingItemClick(listingItem: ListingFromDBObject) {
        val intent = Intent(activity, ListingScreenActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun changeFragment(fragment: Fragment) {
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fc_fragments, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}