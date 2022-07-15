package com.assist.imobilandroidapp.screens.onboarding.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityForgotPasswordBinding
import com.assist.imobilandroidapp.databinding.ActivityLogInBinding
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity
import com.assist.imobilandroidapp.validator.Validator

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var emailErrorMsg: TextView
    private lateinit var sendReqBtn: Button
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initButtons()
        sendReqForgotPass()
    }

    private fun initButtons() {
        binding.btnSendReq.setOnClickListener {
            Toast.makeText(this, getString(R.string.SendResetLink), Toast.LENGTH_SHORT).show()
        }
        binding.tvBackLogin.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, LogInActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun sendReqForgotPass() {
        sendReqBtn = findViewById(R.id.btn_send_req)
        binding.btnSendReq.setOnClickListener {
            if (!Validator(
                    binding.etEmailInput,
                    binding.tvErrorEmail,
                    this,
                    resources
                ).validateEmail()
            ) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.wrong_email),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.correct_email),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

}