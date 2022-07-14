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
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var emailErrorMsg: TextView
    private lateinit var sendReqBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        innitButtons()
        sendReqForgotPass()
    }

    private fun innitButtons() {
        val sendResetLinkButton = findViewById<Button>(R.id.SendResetLinkButton)
        val backToLoginButton = findViewById<TextView>(R.id.BackToLogInButton)
        sendResetLinkButton.setOnClickListener {
            Toast.makeText(this, "Send Reset Link Button", Toast.LENGTH_SHORT).show()
        }
        backToLoginButton.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, LogInActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun validateEmail(): Boolean {
        emailEditText = findViewById(R.id.EditTextEmail)
        emailErrorMsg = findViewById(R.id.ErrorMsgEmail)

        val padding = emailEditText.paddingLeft
        emailEditText.setPadding(padding, padding, padding, padding)
        val email = emailEditText.text.toString()

        if (email.isEmpty()) {
            emailErrorMsg.setText(R.string.empty_field)
            emailErrorMsg.isVisible = true
            emailEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            emailEditText.setPadding(padding, padding, padding, padding)
            return false
        } else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailErrorMsg.setText(R.string.wrong_email)
            emailErrorMsg.isVisible = true
            emailEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            emailEditText.setPadding(padding, padding, padding, padding)
            return false
        } else {
            emailErrorMsg.isVisible = false
            emailEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.loginbox_border)
            emailEditText.setPadding(padding, padding, padding, padding)
            return true
        }
    }

    private fun sendReqForgotPass() {
        sendReqBtn = findViewById(R.id.SendResetLinkButton)
        sendReqBtn.setOnClickListener {
            if (!validateEmail()) {
                Toast.makeText(applicationContext, "Invalid email", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Cool, good job", Toast.LENGTH_LONG).show()
            }
        }
    }

}