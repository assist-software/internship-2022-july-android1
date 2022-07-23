package com.assist.imobilandroidapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.items.CarouselItem

class CarouselItemAdapter(itemList: List<CarouselItem>, private  val onItemCallback: OnItem, private val onFavIconClick: ListingItemAdapter.OnFavIconClickSmallRV):
    RecyclerView.Adapter<CarouselItemAdapter.CarouselViewHolder>() {

    private var viewPool = RecyclerView.RecycledViewPool()
    private var carouselItemList: List<CarouselItem>
    private var userType: Int = 0

    init {
        this.carouselItemList = itemList
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): CarouselViewHolder {
        val view: View = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.parent_layout_carousel, viewGroup, false)

        return CarouselViewHolder(view)
    }

    override fun onBindViewHolder(holder: CarouselViewHolder, position: Int) {
        val carouselItem: CarouselItem = carouselItemList[position]

        holder.carouselTitle.text = carouselItem.carouselTitle

        val layoutManager = LinearLayoutManager(
            holder.listingItemList.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        layoutManager.initialPrefetchItemCount = carouselItem.listingItemList.size

        val listingItemAdapter = ListingItemAdapter(carouselItem.listingItemList,  onFavIconClick)
        holder.apply {
            listingItemList.layoutManager = layoutManager
            listingItemList.adapter = listingItemAdapter
            listingItemList.setRecycledViewPool(viewPool)
            seeEverything.setOnClickListener {
                onItemCallback.onItemClick(carouselItem)
            }
        }

        listingItemAdapter.setUserType(userType)
    }

    fun setUserType(userType: Int) {
        this.userType = userType
    }

    override fun getItemCount(): Int {
        return carouselItemList.size
    }

    inner class CarouselViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val carouselTitle: TextView = itemView.findViewById(R.id.tv_category)
        val listingItemList: RecyclerView = itemView.findViewById(R.id.child_recycler_view)
        val seeEverything: LinearLayout = itemView.findViewById(R.id.ll_see_everything)
    }

    interface OnItem {
        fun onItemClick(carouselItem: CarouselItem)
    }
}