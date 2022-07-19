package com.assist.imobilandroidapp.screens.averageuser.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ListingItemWithDescAdapter
import com.assist.imobilandroidapp.databinding.FragmentLatestBinding
import com.assist.imobilandroidapp.items.ListingItemWithDesc

class LatestFragment : Fragment(), ListingItemWithDescAdapter.OnFavIconCLick {

    private var _binding: FragmentLatestBinding? = null
    private val binding get() = _binding!!
    private val userType: Int = StartFragment.UserTypeConstants.GUEST

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listingsRecyclerView: RecyclerView = binding.rvListings
        val layoutManager = LinearLayoutManager(activity)
        val listingItemsAdapter = ListingItemWithDescAdapter(listingsItemList(), this)

        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = layoutManager
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
        val fragment = FavouritesEmptyFragment()
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fc_fragments, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }
}