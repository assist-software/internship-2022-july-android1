package com.assist.imobilandroidapp.screens.onboarding.login.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.assist.imobilandroidapp.R

class ForgotPasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        val sendResetLinkButton = findViewById<Button>(R.id.SendResetLinkButton)
        val backToLoginButton = findViewById<TextView>(R.id.BackToLogInButton)
        sendResetLinkButton.setOnClickListener {
            Toast.makeText(this, "Send Reset Link Button", Toast.LENGTH_SHORT).show()
        }
        backToLoginButton.setOnClickListener{
            val intent = Intent(this@ForgotPasswordActivity, LogInActivity::class.java)
            this.startActivity(intent)
        }
    }
}