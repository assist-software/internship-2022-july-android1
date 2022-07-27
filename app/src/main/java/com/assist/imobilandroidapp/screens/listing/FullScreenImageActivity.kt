package com.assist.imobilandroidapp.screens.listing

import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityFullScreenImageBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.FitCenter

class FullScreenImageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenImageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFullScreenImageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadImage()
    }

    private var imageUrl: String? = ""
    private var imageUri: Uri = Uri.EMPTY
    private fun loadImage() {
        if (intent.extras!!.getString("imageUrl") != null) {
            imageUrl = intent.extras!!.getString("imageUrl")
            println(imageUrl)
            Glide.with(applicationContext).load(imageUrl).transform(FitCenter()).error(R.drawable.photo_replacement_1)
                .into(binding.ivImage)
        }

        if(intent.extras!!.getString("imageUri") != null) {
            imageUri = intent.extras!!.getString("imageUri")!!.toUri()
            Glide.with(applicationContext).load(imageUri).transform(FitCenter()).error(R.drawable.photo_replacement_1)
                .into(binding.ivImage)
        }
    }
}