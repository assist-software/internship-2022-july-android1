package com.assist.imobilandroidapp.screens.client.main

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.adapters.ViewPagerAdapter
import com.assist.imobilandroidapp.databinding.ActivityClientBinding
import com.assist.imobilandroidapp.screens.add.AddListingActivity
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity
import com.assist.imobilandroidapp.screens.profile.MainProfileActivity
import com.assist.imobilandroidapp.screens.profile.MessagesActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.google.android.material.tabs.TabLayoutMediator

class ClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientBinding
    private var searchQuery = ""
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER

    private val pages: ArrayList<String> = arrayListOf(
        "All Listings",
        "My Listings"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClientBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.vpViewPager.adapter = ViewPagerAdapter(supportFragmentManager, lifecycle)
        binding.vpViewPager.isUserInputEnabled = false

        TabLayoutMediator(binding.tlSeeListings, binding.vpViewPager) { tab, position ->
            tab.text = pages[position]
        }.attach()
        binding.tvName.text = SharedPrefManager.getInstance().fetchName()

        onSearchIconClick()
        onProfileIconClick()
        onAddFABClick()
        onFavoritesIconClick()
        onMessagesBtnClick()
    }

    private fun onMessagesBtnClick() {
        binding.fabSendMessage.setOnClickListener {
            intent = Intent(this, MessagesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onFavoritesIconClick() {
        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            intent = Intent(this, FavoritesActivity::class.java)
            startActivity(intent)
        }
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
                    val intent = Intent(this@ClientActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()

        if (!SharedPrefManager.getInstance().isLoggedIn) {
            intent = Intent(this@ClientActivity, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onAddFABClick() {
        binding.fabAddListing.setOnClickListener {
            intent = Intent(this, AddListingActivity::class.java)
            startActivity(intent)
        }
    }
}