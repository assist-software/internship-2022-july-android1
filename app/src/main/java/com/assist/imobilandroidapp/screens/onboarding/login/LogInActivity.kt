package com.assist.imobilandroidapp.screens.onboarding.login

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.screens.onboarding.forgotpassword.ForgotPasswordActivity


class LogInActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var emailErrorMsg: TextView
    private lateinit var passwdEditText: EditText
    private lateinit var passwdErrorMsg: TextView
    private lateinit var loginBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_in)
        innitButtons()
        loginUser()
    }

    private fun innitButtons() {
        val loginButton = findViewById<Button>(R.id.LoginButton)
        val googleButton = findViewById<Button>(R.id.GoogleButton)
        val forgotPasswordButton = findViewById<TextView>(R.id.ForgotYourPassword)
        val signUpButton = findViewById<TextView>(R.id.SignUpButton)
        val checkBoxRememberMe = findViewById<CheckBox>(R.id.CheckBoxRememberMe)
        loginButton.setOnClickListener {
            Toast.makeText(this, "Login Button", Toast.LENGTH_SHORT).show()
        }
        googleButton.setOnClickListener {
            Toast.makeText(this, "Google Button", Toast.LENGTH_SHORT).show()
        }
        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this@LogInActivity, ForgotPasswordActivity::class.java)
            this.startActivity(intent)
        }
        signUpButton.setOnClickListener {
            Toast.makeText(this, "Sign Up Button", Toast.LENGTH_SHORT).show()
        }
        checkBoxRememberMe.setOnClickListener {
            Toast.makeText(this, "Check box Button", Toast.LENGTH_SHORT).show()
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

    private fun hasDigits(string: String): Boolean {

        for (char in string) {
            if (char.isDigit()) {
                return true
            }
        }
        return false
    }

    private fun validatePasswd(): Boolean {
        passwdEditText = findViewById(R.id.EditTextPassword)
        passwdErrorMsg = findViewById(R.id.ErrorMsgPassword)
        emailEditText = findViewById(R.id.EditTextEmail)
        val padding = emailEditText.paddingLeft

        val passwd = passwdEditText.text.toString()
        passwdEditText.setPadding(padding, padding, padding, padding)

        if (passwd.isEmpty()) {
            passwdErrorMsg.setText(R.string.empty_field)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            passwdEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            passwdEditText.setPadding(padding, padding, padding, padding)
            passwdErrorMsg.isVisible = true
            return false
        } else if (passwd.length < 9) {
            passwdErrorMsg.setText(R.string.insufficient_chars)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            passwdEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            passwdEditText.setPadding(padding, padding, padding, padding)
            passwdErrorMsg.isVisible = true
            return false
        } else if (!hasDigits(passwd)) {
            passwdErrorMsg.setText(R.string.lacks_digit)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            passwdEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            passwdEditText.setPadding(padding, padding, padding, padding)
            passwdErrorMsg.isVisible = true
            return false
        } else {
            passwdErrorMsg.setTextColor(resources.getColor(R.color.gray_500))
            passwdEditText.background =
                ContextCompat.getDrawable(applicationContext, R.drawable.loginbox_border)
            passwdEditText.setPadding(padding, padding, padding, padding)
            passwdErrorMsg.isVisible = false
            return true
        }
    }

    private fun loginUser() {
        loginBtn = findViewById(R.id.LoginButton)
        loginBtn.setOnClickListener {
            if (!validateEmail() or !validatePasswd()) {
                Toast.makeText(applicationContext, "Invalid email or password!", Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(applicationContext, "Cool, good job", Toast.LENGTH_LONG).show()
            }
        }
    }

}