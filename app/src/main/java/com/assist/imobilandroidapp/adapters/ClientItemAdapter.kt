package com.assist.imobilandroidapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.bumptech.glide.Glide
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners

class ClientItemAdapter(
    listingItemWithDescList: MutableList<ListingFromDBObject>,
    private val onButtonsCLick: OnButtonsCLick
) :
    RecyclerView.Adapter<ClientItemAdapter.ClientItemViewHolder>() {

    private var listingItemWithDescList:MutableList<ListingFromDBObject>

    init {
        this.listingItemWithDescList = listingItemWithDescList
    }

    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): ClientItemViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.client_item_listing, viewGroup, false)

        return ClientItemViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClientItemViewHolder, position: Int) {
        val clientItem: ListingFromDBObject = listingItemWithDescList[position]
        val context: Context = holder.listingImage.context

        holder.apply {
            Glide.with(context).load(clientItem.images).override(154, 143)
                .transform(MultiTransformation(CenterCrop(), GranularRoundedCorners(6f, 6f, 6f, 6f)))
                .error(R.drawable.photo_replacement_1).into(listingImage)
            listingTitle.text = clientItem.title
            listingDescription.text = clientItem.shortDescription
            val price = clientItem.price.toString() + " lei"
            listingPrice.text = price

            editBtn.setOnClickListener {
                onButtonsCLick.onEditBtnClick(clientItem)
            }

            deleteBtn.setOnClickListener {
                onButtonsCLick.onDeleteBtnClick(clientItem)
                listingItemWithDescList.remove(clientItem)
                notifyDataSetChanged()
            }
        }
    }


    override fun getItemCount(): Int {
        return listingItemWithDescList.size
    }

    inner class ClientItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val listingImage: ImageView = itemView.findViewById(R.id.iv_photo)
        val listingTitle: TextView = itemView.findViewById(R.id.tv_title)
        val listingDescription: TextView = itemView.findViewById(R.id.tv_description)
        val listingPrice: TextView = itemView.findViewById(R.id.tv_price)
        val editBtn: TextView = itemView.findViewById(R.id.tv_edit)
        val deleteBtn: TextView = itemView.findViewById(R.id.tv_delete)
    }

    interface OnButtonsCLick {
        fun onDeleteBtnClick(item: ListingFromDBObject)
        fun onEditBtnClick(item: ListingFromDBObject)
    }
}