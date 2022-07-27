package com.assist.imobilandroidapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.models.ListingFavoritesFromDB
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

class FavoritesListingAdapter(
    listingFavoritesList: MutableList<ListingFavoritesFromDB>,
    private val onFavIconClick: OnFavIconClick
) :
    RecyclerView.Adapter<FavoritesListingAdapter.ListingFavoritesViewHolder>() {

    private var listingFavoritesList: MutableList<ListingFavoritesFromDB>
    private var userType: Int = 0

    init {
        this.listingFavoritesList = listingFavoritesList
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ListingFavoritesViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_listing_favorites, viewGroup, false)

        return ListingFavoritesViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListingFavoritesViewHolder, position: Int) {
        val listingFavoritesItem: ListingFavoritesFromDB = listingFavoritesList[position]
        val context: Context = holder.listingImage.context

        holder.apply {
            Glide.with(context).load(listingFavoritesItem.images)
                .transform(
                    MultiTransformation(
                        CenterCrop(),
                        GranularRoundedCorners(40f, 40f, 0f, 0f)
                    )
                )
                .error(R.drawable.photo_replacement_1).into(listingImage)
            listingTitle.text = listingFavoritesItem.title
            listingLocation.text = listingFavoritesItem.location
            listingPrice.text = listingFavoritesItem.price.toString() + " lei"

            itemView.setOnClickListener {
                onFavIconClick.onListingClick(listingFavoritesItem)
            }

            favIcon.setOnClickListener {
                var builder = AlertDialog.Builder(context)
                builder.setTitle(context.getString(R.string.favorites))
                    .setMessage(context.getString(R.string.favourites_alert_message))
                    .setCancelable(true)
                    .setPositiveButton(context.getString(R.string.yes)) { dialogInterface, it ->
                        SharedPrefManager.getInstance().fetchUserId()?.let {
                            onFavIconClick.onFavIconClick(listingFavoritesItem)
                            listingFavoritesList.remove(listingFavoritesItem)
                            notifyDataSetChanged()
                        }
                        dialogInterface.cancel()
                    }
                    .setNegativeButton(context.getString(R.string.no)) { dialogInterface, it -> dialogInterface.cancel() }
                    .show()
            }
        }
    }

    override fun getItemCount(): Int {
        return listingFavoritesList.size
    }

    fun setUserType(userType: Int) {
        this.userType = userType
    }

    inner class ListingFavoritesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingImage: ImageView = itemView.findViewById(R.id.iv_photo)
        val listingTitle: TextView = itemView.findViewById(R.id.tv_title)
        val listingLocation: TextView = itemView.findViewById(R.id.tv_location)
        val listingPrice: TextView = itemView.findViewById(R.id.tv_price)
        val favIcon: ImageButton = itemView.findViewById(R.id.ib_favourites)
    }

    interface OnFavIconClick {
        fun onFavIconClick(listingFavoritesFromDB: ListingFavoritesFromDB)
        fun onListingClick(ListingFavoritesFromDB: ListingFavoritesFromDB)
    }
}