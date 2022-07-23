package com.assist.imobilandroidapp.adapters

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

class ListingItemWithDescAdapter(
    listingItemWithDescList: List<ListingFromDBObject>,
    private val onFavIconClick: OnFavIconClick
) :
    RecyclerView.Adapter<ListingItemWithDescAdapter.ListingWithDescViewHolder>() {

    private var listingItemWithDescList: List<ListingFromDBObject>
    private var userType: Int = 0

    init {
        this.listingItemWithDescList = listingItemWithDescList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListingWithDescViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_listing_with_desc, viewGroup, false)

        return ListingWithDescViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingWithDescViewHolder, position: Int) {
        val listingItemWithDesc: ListingFromDBObject = listingItemWithDescList[position]

        holder.apply {
            Glide.with(listingImage).load(listingItemWithDesc.images).into(itemView.findViewById(R.id.iv_photo))
            listingTitle.text = listingItemWithDesc.title
            listingDescription.text = listingItemWithDesc.shortDescription
            listingPrice.text = listingItemWithDesc.price.toString()

            itemView.setOnClickListener {
                onFavIconClick.onListingClick(listingItemWithDesc)
            }

            favIcon.setOnClickListener {
                if (userType == StartFragment.UserTypeConstants.GUEST) {
                    onFavIconClick.onFavIconClick(listingItemWithDesc)
                } else {
                    // Do nothing, at the moment
                }
            }
        }
    }


    override fun getItemCount(): Int {
        return listingItemWithDescList.size
    }

    fun setUserType(userType: Int) {
        this.userType = userType
    }

    inner class ListingWithDescViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingImage: ImageView = itemView.findViewById(R.id.iv_photo)
        val listingTitle: TextView = itemView.findViewById(R.id.tv_title)
        val listingDescription: TextView = itemView.findViewById(R.id.tv_description)
        val listingPrice: TextView = itemView.findViewById(R.id.tv_price)
        val favIcon: ImageButton = itemView.findViewById(R.id.ib_favourites)
    }

    interface OnFavIconClick {
        fun onFavIconClick(listingItemWithDesc: ListingFromDBObject)
        fun onListingClick(ListingItemWithDesc: ListingFromDBObject)
    }
}