package com.assist.imobilandroidapp.adapters

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.bumptech.glide.Glide

class ListingPictureAdapterUri(var context: Context) :
    RecyclerView.Adapter<ListingPictureAdapterUri.ViewHolder>() {

    private var dataList = emptyList<Uri>()

    internal fun setDataList(dataList: List<Uri>) {
        this.dataList = dataList
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var image: ImageView
        init {
            image = itemView.findViewById(R.id.iv_single_image)
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ListingPictureAdapterUri.ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.image_view_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        Glide.with(context)
            .load(data)
            .error(R.drawable.photo_replacement_1)
            .into(holder.image)
    }

    override fun getItemCount() = dataList.size

}