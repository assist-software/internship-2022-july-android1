package com.assist.imobilandroidapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.graphics.drawable.toDrawable
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.items.ListingItem
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment

class ListingItemAdapter(
    listingItemList: List<ListingItem>,
    private val onFavIconClickCallback: OnFavIconClick
) :
    RecyclerView.Adapter<ListingItemAdapter.ListingViewHolder>() {

    private var listingItemList: List<ListingItem>
    private var userType: Int = 0

    fun setUserType(userType: Int) {
        this.userType = userType
    }

    init {
        this.listingItemList = listingItemList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListingViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_listing_no_desc, viewGroup, false)

        return ListingViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingViewHolder, position: Int) {
        val listingItem: ListingItem = listingItemList.get(position)

        holder.apply {
            listingImage.setImageResource(listingItem.listingImage)
            listingTitle.text = listingItem.listingTitle
            listingLocation.text = listingItem.listingLocation
            listingPrice.text = listingItem.listingPrice

            addToFavourites.setOnClickListener {
                if (userType == StartFragment.UserTypeConstants.GUEST) {
                    onFavIconClickCallback.onFavIconClick(listingItem)
                } else {
                    println("This is a logged in user!")
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return listingItemList.size
    }

    inner class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingImage: ImageView = itemView.findViewById(R.id.iv_photo)
        val listingTitle: TextView = itemView.findViewById(R.id.tv_title)
        val listingLocation: TextView = itemView.findViewById(R.id.tv_location)
        val listingPrice: TextView = itemView.findViewById(R.id.tv_price)
        val addToFavourites: ImageButton = itemView.findViewById(R.id.ib_favourites)
    }

    interface OnFavIconClick {
        fun onFavIconClick(listingItem: ListingItem)
    }
}