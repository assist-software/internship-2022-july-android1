package com.assist.imobilandroidapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.items.CarouselItem
import com.assist.imobilandroidapp.items.ListingItem
import com.assist.imobilandroidapp.screens.averageuser.fragments.LatestFragment

class CarouselItemAdapter(itemList: List<CarouselItem>, private  val onItemCallback: OnItem, private val onFavIconClick: ListingItemAdapter.OnFavIconClick):
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
        val carouselItem: CarouselItem = carouselItemList.get(position)

        holder.carouselTitle.text = carouselItem.getCarouselTitle()

        val layoutManager = LinearLayoutManager(
            holder.listingItemList.context,
            LinearLayoutManager.HORIZONTAL,
            false
        )

        layoutManager.initialPrefetchItemCount = carouselItem.getListingItemList().size

        val listingItemAdapter = ListingItemAdapter(carouselItem.getListingItemList(),  onFavIconClick)
        holder.listingItemList.layoutManager = layoutManager
        holder.listingItemList.adapter = listingItemAdapter
        holder.listingItemList.setRecycledViewPool(viewPool)
        holder.seeEverything.setOnClickListener {
            onItemCallback.onItemCLick(carouselItem)
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
        fun onItemCLick(carouselItem: CarouselItem)
    }
}