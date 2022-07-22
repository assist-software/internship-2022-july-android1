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
import com.assist.imobilandroidapp.adapters.ListingItemWithDescAdapter
import com.assist.imobilandroidapp.databinding.FragmentAllListingsBinding
import com.assist.imobilandroidapp.databinding.FragmentLatestBinding
import com.assist.imobilandroidapp.items.ListingItemWithDesc
import com.assist.imobilandroidapp.screens.averageuser.fragments.FavouritesEmptyFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity

class MyListingsFragment : Fragment(), ListingItemWithDescAdapter.OnFavIconCLick {

    private var _binding: FragmentAllListingsBinding? = null
    private val binding get() = _binding!!
    private val userType = StartFragment.UserTypeConstants.LOGGED_IN_USER

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAllListingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listingsRecyclerView: RecyclerView = binding.rvParent
        val listingItemsAdapter = ListingItemWithDescAdapter(listingsItemList(), this)

        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = LinearLayoutManager(activity)
        listingItemsAdapter.setUserType(userType)
    }

    private fun listingsItemList(): List<ListingItemWithDesc> {
        val ListingItemList = ArrayList<ListingItemWithDesc>()

        ListingItemList.add(
            ListingItemWithDesc(
                R.drawable.fav_heart_icon_red,
                getText(R.string.house_random_name_1).toString(),
                getText(R.string.lorem_ipsum).toString(),
                getText(R.string.price_1).toString()
            )
        )

        ListingItemList.add(
            ListingItemWithDesc(
                R.drawable.photo_replacement_1,
                getText(R.string.house_random_name_2).toString(),
                getText(R.string.lorem_ipsum).toString(),
                getText(R.string.price_2).toString()
            )
        )

        ListingItemList.add(
            ListingItemWithDesc(
                R.drawable.logo_assist_tagline,
                getText(R.string.house_random_name_3).toString(),
                getText(R.string.lorem_ipsum).toString(),
                getText(R.string.price_3).toString()
            )
        )

        ListingItemList.add(
            ListingItemWithDesc(
                R.drawable.search_icon,
                getText(R.string.house_random_name_4).toString(),
                getText(R.string.lorem_ipsum).toString(),
                getText(R.string.price_1).toString()
            )
        )
        return ListingItemList
    }

    override fun onFavIconClick(listingItemWithDesc: ListingItemWithDesc) {
        // Do nothing
    }

    override fun onListingClick(ListingItemWithDesc: ListingItemWithDesc) {
        val intent = Intent(activity, ListingScreenActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}