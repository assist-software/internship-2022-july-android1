package com.assist.imobilandroidapp.screens.profile

import android.content.Intent
import android.os.Bundle
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.databinding.ActivityMessagesBinding
import com.assist.imobilandroidapp.screens.averageuser.fragments.StartFragment
import com.assist.imobilandroidapp.screens.client.main.ClientActivity
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity
import com.assist.imobilandroidapp.screens.search.SearchActivity

class MessagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding
    private var searchQuery: String = ""
    private var userType = StartFragment.UserTypeConstants.LOGGED_IN_USER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onMessagesBtnClick()
        onFavoritesIconClick()
        onProfileIconClick()
        onProfileMenuBtnClick()
        startExploring()
        onSearchIconClick()
    }

    private fun onProfileMenuBtnClick() {
        binding.llMenuProfile.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
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

    private fun onProfileIconClick() {
        binding.toolbar.ivProfilePic.setOnClickListener {
            intent = Intent(this, MainProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun startExploring() {
        binding.btnStartExploring.setOnClickListener {
            val intent = Intent(applicationContext, ClientActivity::class.java)
            startActivity(intent)
        }
    }

    private fun onSearchIconClick() {
        binding.toolbar.ivSearchIcon.setOnClickListener {
            binding.svSearch.isVisible = true
            binding.svSearch.setOnQueryTextListener(object: SearchView.OnQueryTextListener {
                override fun onQueryTextChange(p0: String?): Boolean {
                    return false
                }

                override fun onQueryTextSubmit(text: String): Boolean {
                    searchQuery = text
                    val intent = Intent(applicationContext, SearchActivity::class.java)
                    intent.putExtra("searchQuery", searchQuery)
                    intent.putExtra("userType", userType)
                    startActivity(intent)
                    return false
                }
            })
        }
    }
}