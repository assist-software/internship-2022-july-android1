package com.assist.imobilandroidapp.screens.averageuser.main

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView.OnQueryTextListener
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityAverageUserBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.FavouritesEmptyFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.LoginDialogFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.search.SearchActivity

class AverageUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAverageUserBinding
    private var searchQuery: String = ""
    private var userType = StartFragment.UserTypeConstants.GUEST

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAverageUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onFavouritesIconCLick()
        onSearchIconClick()
        onProfileIconClick()
        replaceFragment(startFragment)
    }

    private val startFragment = StartFragment()
    private val favouritesFragment = FavouritesEmptyFragment()

    private fun replaceFragment(fragment: Fragment) {
        if (fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fc_fragments, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun onSearchIconClick() {
        binding.toolbar.ivSearchIcon.setOnClickListener {
            binding.svSearch.isVisible = true
            binding.svSearch.setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(text: String): Boolean {
                    searchQuery = text
                    val intent = Intent(this@AverageUserActivity, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }

    private fun onFavouritesIconCLick() {
        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            replaceFragment(favouritesFragment)
        }
    }

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            LoginDialogFragment().show(supportFragmentManager, LoginDialogFragment.TAG)
        }
    }
}