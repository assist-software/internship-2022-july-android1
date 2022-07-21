package com.assist.imobilandroidapp.screens.client.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.CarouselItemAdapter
import com.assist.imobilandroidapp.adapters.ListingItemAdapter
import com.assist.imobilandroidapp.databinding.FragmentAllListingsBinding
import com.assist.imobilandroidapp.databinding.FragmentStartBinding
import com.assist.imobilandroidapp.items.CarouselItem
import com.assist.imobilandroidapp.items.ListingItem
import com.assist.imobilandroidapp.screens.averageuser.fragments.FavouritesEmptyFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.LatestFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity

class AllListingsFragment : Fragment(), CarouselItemAdapter.OnItem, ListingItemAdapter.OnFavIconClick {

    private var _binding: FragmentAllListingsBinding? = null
    private val binding get() = _binding!!
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER
    private var carouselItemAdapter: CarouselItemAdapter ?= null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllListingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val carouselRecyclerViewItem: RecyclerView = binding.rvParent
        val layoutManager = LinearLayoutManager(activity)
        carouselItemAdapter = CarouselItemAdapter(carouselItemList(), this, this)
        carouselItemAdapter!!.setUserType(userType)

        carouselRecyclerViewItem.adapter = carouselItemAdapter
        carouselRecyclerViewItem.layoutManager = layoutManager
    }

    private fun carouselItemList(): List<CarouselItem> {
        val categoryList = ArrayList<CarouselItem>()

        val latestListings = CarouselItem(getText(R.string.latest).toString(), listingItemList())
        categoryList.add(latestListings)

        val bigHouse = CarouselItem(getText(R.string.big_house).toString(), listingItemList())
        categoryList.add(bigHouse)

        val smallHouse = CarouselItem(getText(R.string.small_house).toString(), listingItemList())
        categoryList.add(smallHouse)

        return categoryList
    }

    private fun listingItemList(): List<ListingItem> {
        val ListingItemList = ArrayList<ListingItem>()

        ListingItemList.add(
            ListingItem(
                R.drawable.fav_heart_icon_red,
                getText(R.string.house_random_name_1).toString(),
                getText(R.string.location_suceava).toString(),
                getText(R.string.price_1).toString()
            )
        )

        ListingItemList.add(
            ListingItem(
                R.drawable.photo_replacement_1,
                getText(R.string.house_random_name_2).toString(),
                getText(R.string.location_suceava).toString(),
                getText(R.string.price_2).toString()
            )
        )

        ListingItemList.add(
            ListingItem(
                R.drawable.logo_assist_tagline,
                getText(R.string.house_random_name_3).toString(),
                getText(R.string.location_bucuresti).toString(),
                getText(R.string.price_3).toString()
            )
        )

        ListingItemList.add(
            ListingItem(
                R.drawable.search_icon,
                getText(R.string.house_random_name_4).toString(),
                getText(R.string.location_cluj).toString(),
                getText(R.string.price_1).toString()
            )
        )
        return ListingItemList
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemCLick(carouselItem: CarouselItem) {
        // Do nothing
    }

    override fun onFavIconClick(listingItem: ListingItem) {
        // Do nothing
    }

    override fun onListingItemClick(listingItem: ListingItem) {
        val intent = Intent(activity, ListingScreenActivity::class.java)
        startActivity(intent)
    }
}