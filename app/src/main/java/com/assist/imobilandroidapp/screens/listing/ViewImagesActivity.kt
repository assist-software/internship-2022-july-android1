package com.assist.imobilandroidapp.screens.listing

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ListingPictureAdapter
import com.assist.imobilandroidapp.databinding.ActivityViewImagesBinding


class ViewImagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImagesBinding

    private lateinit var listingPictureAdapter: ListingPictureAdapter
    private var dataList = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initOnExitButtonClick()
        initRecycleView()
        initImageListing()
    }

    private fun initOnExitButtonClick() {
        binding.toolbar.ivExitIcon.setOnClickListener {
            finish()
        }
    }

    private fun initRecycleView() {
        binding.rvViewImages.layoutManager = GridLayoutManager(applicationContext, 2)
        listingPictureAdapter = ListingPictureAdapter(applicationContext)
        binding.rvViewImages.adapter = listingPictureAdapter
    }

    private fun initImageListing() {
        dataList.add(
            0,
            "https://images.unsplash.com/photo-1554995207-c18c203602cb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80"
        )
        dataList.add(
            1,
            "https://images.unsplash.com/photo-1554995207-c18c203602cb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80"
        )
        dataList.add(
            2,
            "https://images.unsplash.com/photo-1554995207-c18c203602cb?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=2070&q=80"
        )
        listingPictureAdapter.setDataList(dataList)
    }


}