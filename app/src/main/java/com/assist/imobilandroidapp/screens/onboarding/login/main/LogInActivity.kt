package com.assist.imobilandroidapp.screens.onboarding.login.main

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.R


class LogInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        val loginButton = findViewById<Button>(R.id.LoginButton)
        val googleButton = findViewById<Button>(R.id.GoogleButton)
        val forgotPasswordButton = findViewById<TextView>(R.id.ForgotYourPassword)
        val signUpButton = findViewById<TextView>(R.id.SignUpButton)
        val checkBoxRememberMe = findViewById<CheckBox>(R.id.CheckBoxRememberMe)
        loginButton.setOnClickListener {
            Toast.makeText(this, "Login Button", Toast.LENGTH_SHORT).show()
        }
        googleButton.setOnClickListener{
            Toast.makeText(this, "Google Button", Toast.LENGTH_SHORT).show()
        }
        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this@LogInActivity, ForgotPasswordActivity::class.java)
            this.startActivity(intent)
        }
        signUpButton.setOnClickListener{
            Toast.makeText(this, "Sign Up Button", Toast.LENGTH_SHORT).show()
        }
        checkBoxRememberMe.setOnClickListener {
            Toast.makeText(this, "Check box Button", Toast.LENGTH_SHORT).show()
        }
    }

}