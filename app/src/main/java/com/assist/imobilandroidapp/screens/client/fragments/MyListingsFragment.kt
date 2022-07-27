package com.assist.imobilandroidapp.screens.client.fragments

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
import com.assist.imobilandroidapp.adapters.ClientItemAdapter
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.assist.imobilandroidapp.apiinterface.models.MyListingsRequest
import com.assist.imobilandroidapp.databinding.FragmentMyListingsBinding
import com.assist.imobilandroidapp.screens.client.main.EditMyListing
import com.assist.imobilandroidapp.storage.SharedPrefManager
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MyListingsFragment : Fragment(), ClientItemAdapter.OnButtonsCLick {

    private var _binding: FragmentMyListingsBinding? = null
    private val binding get() = _binding!!
    private val myListings: ArrayList<ListingFromDBObject> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMyListingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getMyListings()
    }

    private fun getMyListings() {
        val myId = SharedPrefManager.getInstance().fetchUserId()
        val token = SharedPrefManager.getInstance().fetchToken()

        RetrofitClient.instance.getMyListings(
            MyListingsRequest(null, null, null, null, null, -1, myId),
            token
        ).enqueue(object : Callback<List<ListingFromDBObject>> {
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
                            myListings.addAll(response.body()!!)
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

    private fun initRV() {
        val listingsRecyclerView: RecyclerView = binding.rvParent
        val listingItemsAdapter = ClientItemAdapter(myListings, this)

        listingsRecyclerView.adapter = listingItemsAdapter
        listingsRecyclerView.layoutManager = LinearLayoutManager(activity)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onDeleteBtnClick(item: ListingFromDBObject) {
        val listingId = item.id
        val token = SharedPrefManager.getInstance().fetchToken()

        RetrofitClient.instance.deleteMyListing(listingId, token)
            .enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    when(response.code()) {
                        200 -> {
                            Toast.makeText(
                                activity,
                                getText(R.string.delete_success).toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }

                        else -> {
                            Toast.makeText(
                                activity,
                                getText(R.string.something_wrong).toString() + response.code()
                                    .toString(),
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Toast.makeText(
                        activity,
                        getText(R.string.something_wrong).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            })

    }

    override fun onEditBtnClick(item: ListingFromDBObject) {
        val intent = Intent(activity, EditMyListing::class.java)
        intent.putExtra("id", item.id)
        startActivity(intent)
    }
}