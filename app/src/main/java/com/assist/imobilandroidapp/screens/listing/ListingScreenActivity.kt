package com.assist.imobilandroidapp.screens.listing

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityListingScreenBinding
import com.assist.imobilandroidapp.screens.onboarding.forgotpassword.ForgotPasswordActivity

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
            Toast.makeText(this, getString(R.string.placeholder), Toast.LENGTH_SHORT).show()
            val intent = Intent(this, ViewImagesActivity::class.java)
            this.startActivity(intent)
        }
        binding.btnShare.setOnClickListener {
            Toast.makeText(this, getString(R.string.share), Toast.LENGTH_SHORT).show()
        }
        binding.btnPurchase.setOnClickListener {
            Toast.makeText(this, getString(R.string.purchase), Toast.LENGTH_SHORT).show()
        }
        binding.btnHeart.setOnClickListener {
            Toast.makeText(this, getString(R.string.favorites), Toast.LENGTH_SHORT).show()
        }
        binding.btnContactSeller.setOnClickListener {
            Toast.makeText(this, getString(R.string.contact_seller), Toast.LENGTH_SHORT).show()
        }
    }
}