package com.assist.imobilandroidapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

class ListingItemAdapter(
    listingItemList: List<ListingFromDBObject>,
    private val onFavIconClickCallback: OnFavIconClickSmallRV
) :
    RecyclerView.Adapter<ListingItemAdapter.ListingViewHolder>() {

    private var listingItemList: List<ListingFromDBObject>
    private var userType: Int = 0
    private val listingLimit = 10

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
        val listingItem: ListingFromDBObject = listingItemList[position]
        val context: Context = holder.listingImage.context

        holder.apply {
            Glide.with(context).load(listingItem.images)
                .override(150, 68)
                .transform(MultiTransformation(CenterCrop(), GranularRoundedCorners(12f, 12f, 0f, 0f)))
                .error(R.drawable.photo_replacement_1).into(itemView.findViewById(R.id.iv_photo))
            listingTitle.text = listingItem.title
            listingLocation.text = listingItem.location
            listingPrice.text = listingItem.price.toString()

            itemView.setOnClickListener {
                onFavIconClickCallback.onListingItemClick(listingItem)
            }

            addToFavourites.setOnClickListener {
                if (userType == StartFragment.UserTypeConstants.GUEST) {
                    onFavIconClickCallback.onFavIconClick(listingItem)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return if(listingItemList.size > listingLimit) {
            listingLimit
        } else {
            listingItemList.size
        }
    }

    inner class ListingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingImage: ImageView = itemView.findViewById(R.id.iv_photo)
        val listingTitle: TextView = itemView.findViewById(R.id.tv_title)
        val listingLocation: TextView = itemView.findViewById(R.id.tv_location)
        val listingPrice: TextView = itemView.findViewById(R.id.tv_price)
        val addToFavourites: ImageButton = itemView.findViewById(R.id.ib_favourites)
    }

    interface OnFavIconClickSmallRV {
        fun onFavIconClick(listingItem: ListingFromDBObject)
        fun onListingItemClick(listingItem: ListingFromDBObject)
    }
}