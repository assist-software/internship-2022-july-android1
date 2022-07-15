package com.assist.imobilandroidapp.screens.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityMainBinding
import com.assist.imobilandroidapp.screens.onboarding.resetpassword.main.ResetPasswordActivity
import com.assist.imobilandroidapp.screens.onboarding.singup.main.SignUpActivity


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUpBtn.setOnClickListener {
            sendMeToSignUp()
        }

        binding.resetPasswdBtn.setOnClickListener {
            sendMeToResetPassword()
        }
    }
    fun sendMeToSignUp() {
        val intent = Intent(this@MainActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun sendMeToResetPassword() {
        val intent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
        startActivity(intent)
    }
}