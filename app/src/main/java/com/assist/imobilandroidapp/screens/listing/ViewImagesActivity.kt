package com.assist.imobilandroidapp.screens.listing

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ListingPictureAdapter
import com.assist.imobilandroidapp.adapters.ListingPictureAdapterUri
import com.assist.imobilandroidapp.databinding.ActivityViewImagesBinding


class ViewImagesActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewImagesBinding
    private lateinit var listingPictureAdapterForUrl: ListingPictureAdapter
    private lateinit var listingPictureAdapterForUri: ListingPictureAdapterUri
    private var dataList = mutableListOf<String>()
    private var uriDataList = mutableListOf<Uri>()
    private var listingImages: ArrayList<Uri> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initOnExitButtonClick()
//        initRecycleView()
//        initImageListing()
        showImages()
    }

    private fun showImages() {
        val bundle = intent.extras
        if(bundle != null) {
            initRecycleViewForUri()
            listingImages = bundle.getParcelableArrayList<Uri>("images") as ArrayList<Uri>
            listingPictureAdapterForUri.setDataList(listingImages)
        }
        else {
            initRecycleViewForUrl()
            initImageListing()
        }
    }

    private fun initOnExitButtonClick() {
        binding.toolbar.ivExitIcon.setOnClickListener {
            finish()
        }
    }

    private fun initRecycleViewForUrl() {
        binding.rvViewImages.layoutManager = GridLayoutManager(applicationContext, 2)
        listingPictureAdapterForUrl = ListingPictureAdapter(applicationContext)
        binding.rvViewImages.adapter = listingPictureAdapterForUrl
    }

    private fun initRecycleViewForUri() {
        binding.rvViewImages.layoutManager = GridLayoutManager(applicationContext, 2)
        listingPictureAdapterForUri = ListingPictureAdapterUri(applicationContext)
        binding.rvViewImages.adapter = listingPictureAdapterForUri
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
        listingPictureAdapterForUrl.setDataList(dataList)
    }
}