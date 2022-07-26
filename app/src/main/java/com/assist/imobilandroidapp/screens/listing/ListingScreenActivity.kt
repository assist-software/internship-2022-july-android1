package com.assist.imobilandroidapp.screens.listing

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityListingScreenBinding
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity

class ListingScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListingScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListingScreenBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initButtons()
    }

    private fun initButtons() {

        binding.ivMorePictureButton.setOnClickListener {
            morePictureButton()
        }
        binding.btnShare.setOnClickListener {
            shareButton()
        }
        binding.btnPurchase.setOnClickListener {
            purchaseButton()
        }
        binding.btnHeart.setOnClickListener {
            heartButton()
        }
        binding.btnContactSeller.setOnClickListener {
            contactSellerButton()
        }
        binding.toolbar.cvProfilePic.setOnClickListener {
            profilePicButton()
        }
    }

    private fun morePictureButton() {
        Toast.makeText(this, getString(R.string.placeholder), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, ViewImagesActivity::class.java)
        this.startActivity(intent)
    }

    private fun profilePicButton() {
        Toast.makeText(this, getString(R.string.placeholder), Toast.LENGTH_SHORT).show()
        val intent = Intent(this, MainProfileActivity::class.java)
        this.startActivity(intent)
    }

    private fun contactSellerButton() {
        Toast.makeText(this, getString(R.string.contact_seller), Toast.LENGTH_SHORT).show()
    }

    private fun heartButton() {
        Toast.makeText(this, getString(R.string.favorites), Toast.LENGTH_SHORT).show()
    }

    private fun purchaseButton() {
        Toast.makeText(this, getString(R.string.purchase), Toast.LENGTH_SHORT).show()
    }

    private fun shareButton() {
        Toast.makeText(this, getString(R.string.share), Toast.LENGTH_SHORT).show()
    }
}