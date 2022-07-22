package com.assist.imobilandroidapp.screens.client.main

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.adapters.ViewPagerAdapter
import com.assist.imobilandroidapp.databinding.ActivityClientBinding
import com.assist.imobilandroidapp.screens.client.fragments.AllListingsFragment
import com.assist.imobilandroidapp.screens.client.fragments.MyListingsFragment
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.google.android.material.tabs.TabLayoutMediator

class ClientActivity : AppCompatActivity() {

    private lateinit var binding: ActivityClientBinding
    private var searchQuery = ""

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

        TabLayoutMediator(binding.tlSeeListings, binding.vpViewPager) {
            tab, position -> tab.text = pages[position]
        }.attach()

        onSearchIconClick()
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
                    Toast.makeText(
                        applicationContext,
                        getText(R.string.searched_for).toString() + searchQuery,
                        Toast.LENGTH_SHORT
                    ).show()
                    return false
                }
            })
        }
    }

    override fun onStart() {
        super.onStart()

        if(!SharedPrefManager.getInstance().isLoggedIn) {
            intent = Intent(this@ClientActivity, LogInActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}