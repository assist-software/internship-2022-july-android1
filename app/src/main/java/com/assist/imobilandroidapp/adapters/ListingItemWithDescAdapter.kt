package com.assist.imobilandroidapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.items.ListingItemWithDesc
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment

class ListingItemWithDescAdapter(listingItem2List: List<ListingItemWithDesc>, private val onFavIconClick: ListingItemWithDescAdapter.OnFavIconCLick) :
    RecyclerView.Adapter<ListingItemWithDescAdapter.ListingView2Holder>() {

    private var listingItem2List: List<ListingItemWithDesc>
    private var userType: Int = 0

    init {
        this.listingItem2List = listingItem2List
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListingView2Holder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_listing_with_desc, viewGroup, false)

        return ListingView2Holder(view)
    }

    override fun onBindViewHolder(holder: ListingView2Holder, position: Int) {
        val listingItem2: ListingItemWithDesc = listingItem2List.get(position)

        holder.listingImage.setImageResource(listingItem2.getListingImage())
        holder.listingTitle.text = listingItem2.getListingTitle()
        holder.listingDescription.text = listingItem2.getListingDescription()
        holder.listingPrice.text = listingItem2.getListingPrice()
        holder.favIcon.setOnClickListener {
            if(userType == StartFragment.UserTypeConstants.GUEST){
                onFavIconClick.onFavIconClick(listingItem2)
            }
            else {
                // Do nothing, at the moment
            }
        }
    }

    override fun getItemCount(): Int {
        return listingItem2List.size
    }

    fun setUserType(userType: Int) {
        this.userType = userType
    }

    inner class ListingView2Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingImage: ImageView = itemView.findViewById(R.id.iv_photo)
        val listingTitle: TextView = itemView.findViewById(R.id.tv_title)
        val listingDescription: TextView = itemView.findViewById(R.id.tv_description)
        val listingPrice: TextView = itemView.findViewById(R.id.tv_price)
        val favIcon: ImageButton = itemView.findViewById(R.id.ib_favourites)
    }

    interface OnFavIconCLick {
        fun onFavIconClick(listingItemWithDesc: ListingItemWithDesc)
    }
}