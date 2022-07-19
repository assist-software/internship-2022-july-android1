package com.assist.imobilandroidapp.screens.averageuser.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import android.widget.SearchView.OnQueryTextListener
import android.widget.Toast
import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityAverageUserBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.FavouritesEmptyFragment
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment

class AverageUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAverageUserBinding
    private var searchQuery: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAverageUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbar.ivFavouritesIcon.setOnClickListener {
            replaceFragment(favouritesFragment)
        }

        onSearchIconClick()
        replaceFragment(startFragment)
    }

    private val startFragment = StartFragment()
    private val favouritesFragment = FavouritesEmptyFragment()

    private fun replaceFragment(fragment: Fragment) {
        if(fragment != null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fc_fragments, fragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }
    }

    private fun onSearchIconClick() {
        binding.toolbar.ivSearchIcon.setOnClickListener {
            binding.svSearch.isVisible = true
            binding.svSearch.setOnQueryTextListener(object: OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(text: String): Boolean {
                    searchQuery = text
                    binding.svSearch.isGone = true
                    Toast.makeText(applicationContext, getText(R.string.searched_for).toString() + searchQuery, Toast.LENGTH_SHORT).show()
                    return false
                }
            })
        }
    }
}