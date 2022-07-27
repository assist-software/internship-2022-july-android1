package com.assist.imobilandroidapp.screens.profile

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.databinding.ActivityMessagesBinding
import com.assist.imobilandroidapp.screens.favorites.FavoritesActivity

class MessagesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMessagesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMessagesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onMessagesBtnClick()
        onFavoritesIconClick()
        onProfileIconClick()
        onProfileMenuBtnClick()
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
}