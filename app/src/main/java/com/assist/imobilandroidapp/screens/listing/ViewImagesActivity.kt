package com.assist.imobilandroidapp.screens.listing

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ListingPictureAdapter
import com.assist.imobilandroidapp.adapters.ListingPictureAdapterUri
import com.assist.imobilandroidapp.databinding.ActivityViewImagesBinding


class ViewImagesActivity : AppCompatActivity(), ListingPictureAdapter.OnImageClickUrl,
    ListingPictureAdapterUri.OnImageCLickUri {

    private lateinit var binding: ActivityViewImagesBinding
    private lateinit var listingPictureAdapterForUrl: ListingPictureAdapter
    private lateinit var listingPictureAdapterForUri: ListingPictureAdapterUri
    private var listingImagesUri: ArrayList<Uri> = ArrayList()
    private var listingImagesUrl: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewImagesBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initOnExitButtonClick()
        showImages()
    }

    private fun showImages() {
        val bundle = intent.extras
        if (bundle != null) {
            if (bundle.getParcelableArrayList<Uri>("images") == null && bundle.getStringArrayList("imagesURL") != null) {
                initRecycleViewForUrl()
                listingImagesUrl = bundle.getStringArrayList("imagesURL") as ArrayList<String>
                listingPictureAdapterForUrl.setDataList(listingImagesUrl)
            } else if (bundle.getParcelableArrayList<Uri>("images") != null && bundle.getStringArrayList(
                    "imagesURL"
                ) == null
            ) {
                initRecycleViewForUri()
                listingImagesUri = bundle.getParcelableArrayList<Uri>("images") as ArrayList<Uri>
                listingPictureAdapterForUri.setDataList(listingImagesUri)
            } else {
                Toast.makeText(this, R.string.something_wrong, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun initOnExitButtonClick() {
        binding.toolbar.ivExitIcon.setOnClickListener {
            finish()
        }
    }

    private fun initRecycleViewForUrl() {
        binding.rvViewImages.layoutManager = GridLayoutManager(applicationContext, 2)
        listingPictureAdapterForUrl = ListingPictureAdapter(applicationContext, this)
        binding.rvViewImages.adapter = listingPictureAdapterForUrl
    }

    private fun initRecycleViewForUri() {
        binding.rvViewImages.layoutManager = GridLayoutManager(applicationContext, 2)
        listingPictureAdapterForUri = ListingPictureAdapterUri(applicationContext, this)
        binding.rvViewImages.adapter = listingPictureAdapterForUri
    }

    override fun onUrlImageClick(imageUrl: String) {
        val intent = Intent(this, FullScreenImageActivity::class.java)
        intent.putExtra("imageUrl", imageUrl)
        startActivity(intent)
    }

    override fun onUriImageClick(image: Uri) {
        val intent = Intent(this, FullScreenImageActivity::class.java)
        intent.putExtra("imageUri", image.toString())
        startActivity(intent)
    }

}