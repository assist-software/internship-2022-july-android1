package com.assist.imobilandroidapp.screens.add

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityListingConfirmedBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.client.main.ClientActivity
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.profile.MessagesActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity

class ListingConfirmedActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListingConfirmedBinding
    private var searchQuery: String = ""
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListingConfirmedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onSearchIconClick()
        onProfileIconClick()
        onHomeBtnClick()
        onAddNewBtnClick()
        onMessageClick()
        onFavIconClick()
    }

    private fun onSearchIconClick() {
        binding.toolbar.ivSearchIcon.setOnClickListener {
            binding.svSearch.isVisible = true
            binding.svSearch.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(text: String): Boolean {
                    searchQuery = text
                    binding.svSearch.isGone = true
                    val intent = Intent(this@ListingConfirmedActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onHomeBtnClick() {
        binding.btnHome.setOnClickListener {
            val intent = Intent(this, ClientActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onAddNewBtnClick() {
        binding.btnAddNewListing.setOnClickListener {
            val intent = Intent(this, AddListingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onMessageClick() {
        binding.fabSendMessage.setOnClickListener {
            intent = Intent(applicationContext, MessagesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onFavIconClick() {
        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            intent = Intent(applicationContext, FavoritesActivity::class.java)
            startActivity(intent)
        }
    }
}