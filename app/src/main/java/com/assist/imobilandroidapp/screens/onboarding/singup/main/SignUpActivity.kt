package com.assist.imobilandroidapp.screens.onboarding.singup.main

import android.content.Context
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.*
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.google.android.material.textfield.TextInputEditText

private val Drawable.setOnClickListener: Unit
    get() {}

class SignUpActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var emailErrorMsg: TextView
    private lateinit var passwdEditText: EditText
    private lateinit var passwdErrorMsg: TextView
    private lateinit var signUpBtn: AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        registerNewUser()
    }

    private fun validateEmail() : Boolean {
        emailEditText = findViewById(R.id.TextInputEditText_email)
        emailErrorMsg = findViewById(R.id.tv_wrong_email_input)

        val padding = emailEditText.paddingLeft
        emailEditText.setPadding(padding, padding, padding, padding)

        val email = emailEditText.text.toString()

        if(email.isEmpty()) {
            emailErrorMsg.setText(R.string.empty_field)
            emailErrorMsg.isVisible = true
            emailEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            emailEditText.setPadding(padding,padding,padding,padding)
            return false
        }
        else if(!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            emailErrorMsg.setText(R.string.wrong_email)
            emailErrorMsg.isVisible = true
            emailEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            emailEditText.setPadding(padding,padding,padding,padding)
            return false
        }
        else {
            emailErrorMsg.isVisible = false
            emailEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            emailEditText.setPadding(padding,padding,padding,padding)
            return true
        }
    }

    private fun hasDigits(string: String) : Boolean {

        for(char in string) {
            if(char.isDigit()) {
                return true
            }
        }

        return false
    }

    private fun validatePasswd() : Boolean {
        passwdEditText = findViewById(R.id.TextInputEditText_passwd)
        passwdErrorMsg = findViewById(R.id.tv_invalid_passwd)
        emailEditText = findViewById(R.id.TextInputEditText_email)
        val padding = emailEditText.paddingLeft

        val passwd = passwdEditText.text.toString()
        passwdEditText.setPadding(padding, padding, padding, padding)

        if(passwd.isEmpty()) {
            passwdErrorMsg.setText(R.string.empty_field)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            passwdEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            passwdEditText.setPadding(padding,padding,padding,padding)
            return false
        }
        else if(passwd.length < 9){
            passwdErrorMsg.setText(R.string.insufficient_chars)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            passwdEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            passwdEditText.setPadding(padding,padding,padding,padding)
            return false
        }
        else if(!hasDigits(passwd)) {
            passwdErrorMsg.setText(R.string.lacks_digit)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            passwdEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            passwdEditText.setPadding(padding,padding,padding,padding)
            return false
        }
        else {
            passwdErrorMsg.setText(R.string.pwd_helper)
            passwdErrorMsg.setTextColor(resources.getColor(R.color.gray_500))
            passwdEditText.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            passwdEditText.setPadding(padding,padding,padding,padding)
            return true
        }
    }

    private fun registerNewUser() {
        signUpBtn = findViewById(R.id.btn_sign_up)
        signUpBtn.setOnClickListener {
            if(!validateEmail() or !validatePasswd()) {
                Toast.makeText(applicationContext, "Invalid email or password!", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(applicationContext, "Cool, good job", Toast.LENGTH_LONG).show()
            }
        }
    }

}