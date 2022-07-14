package com.assist.imobilandroidapp.screens.main

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.screens.onboarding.resetpassword.main.ResetPasswordActivity
import com.assist.imobilandroidapp.screens.onboarding.singup.main.SignUpActivity


class MainActivity : AppCompatActivity() {

    private lateinit var signUp: Button
    private lateinit var resetPasswd: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        signUp = findViewById(R.id.sign_up_btn)
        signUp.setOnClickListener {
            btn1()
        }

        resetPasswd = findViewById(R.id.reset_passwd_btn)
        resetPasswd.setOnClickListener {
            btn2()
        }

    }
    fun btn1() {
        val intent = Intent(this@MainActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun btn2() {
        val intent = Intent(this@MainActivity, ResetPasswordActivity::class.java)
        startActivity(intent)
    }
}