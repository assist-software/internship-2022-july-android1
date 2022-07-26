package com.assist.imobilandroidapp.screens.averageuser.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ListingItemWithDescAdapter
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.assist.imobilandroidapp.databinding.FragmentLatestBinding
import com.assist.imobilandroidapp.screens.listing.ListingScreenActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatestFragment : Fragment(), ListingItemWithDescAdapter.OnFavIconClick {

    private var _binding: FragmentLatestBinding? = null
    private val binding get() = _binding!!
    private val userType: Int = StartFragment.UserTypeConstants.GUEST

    val bigHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val smallHouseListings: ArrayList<ListingFromDBObject> = arrayListOf()
    val foundListings: ArrayList<ListingFromDBObject> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLatestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = this.arguments
        if (bundle != null) {
            binding.tvTitle.text = bundle.getString("title")
            println(bundle.getString("title"))
        }
        getListings()
    }

    private fun initRV() {
        val listingsRecyclerView: RecyclerView = binding.rvListings
        var listingItemsAdapter = ListingItemWithDescAdapter(emptyList(), this)

        when (arguments?.getString("title")) {
            getText(R.string.latest).toString() -> listingItemsAdapter =
                ListingItemWithDescAdapter(foundListings, this)

            getText(R.string.big_house).toString() -> listingItemsAdapter =
                ListingItemWithDescAdapter(bigHouseListings, this)

            getText(R.string.small_house).toString() -> listingItemsAdapter =
                ListingItemWithDescAdapter(smallHouseListings, this)
        }

        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = LinearLayoutManager(activity)
        listingItemsAdapter.setUserType(userType)
    }

    private fun getListings() {
        RetrofitClient.instance.getListings().enqueue(object :
            Callback<List<ListingFromDBObject>> {
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
                                when (item.category) {
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

    override fun onFavIconClick(listingItemWithDesc: ListingFromDBObject) {
        val fragment = FavouritesEmptyFragment()
        val fragmentManager = activity?.supportFragmentManager
        val fragmentTransaction = fragmentManager?.beginTransaction()
        fragmentTransaction?.replace(R.id.fc_fragments, fragment)
        fragmentTransaction?.addToBackStack(null)
        fragmentTransaction?.commit()
    }

    override fun onListingClick(ListingItemWithDesc: ListingFromDBObject) {
        val intent = Intent(activity, ListingScreenActivity::class.java)
        startActivity(intent)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}