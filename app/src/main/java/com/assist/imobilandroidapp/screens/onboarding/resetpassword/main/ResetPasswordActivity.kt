package com.assist.imobilandroidapp.screens.onboarding.resetpassword.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.core.view.isGone
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var editTextPasswd : EditText
    private lateinit var editTextPasswdErrorMsg : TextView
    private lateinit var editTextConfirmPasswd : EditText
    private lateinit var editTextConfirmPasswdErrorMsg: TextView
    private lateinit var confirmBtn : AppCompatButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reset_password)

        sendNewPasswd()
    }

    private fun hasDigits(string: String) : Boolean {

        for(char in string) {
            if(char.isDigit()) {
                return true
            }
        }

        return false
    }

    private fun validateNewPasswd() : Boolean {
        editTextPasswd = findViewById(R.id.TextInputEditText_new_passwd)
        editTextPasswdErrorMsg = findViewById(R.id.tv_reset_invalid_passwd)

        val padding = editTextPasswd.paddingLeft

        val passwd = editTextPasswd.text.toString()

        if(passwd.isEmpty()) {
            editTextPasswdErrorMsg.setText(R.string.empty_field)
            editTextPasswdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            editTextPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            editTextPasswd.setPadding(padding,padding,padding,padding)
            return false
        }
        else if(passwd.length < 9){
            editTextPasswdErrorMsg.setText(R.string.insufficient_chars)
            editTextPasswdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            editTextPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            editTextPasswd.setPadding(padding,padding,padding,padding)
            return false
        }
        else if(!hasDigits(passwd)) {
            editTextPasswdErrorMsg.setText(R.string.lacks_digit)
            editTextPasswdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
            editTextPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
            editTextPasswd.setPadding(padding,padding,padding,padding)
            return false
        }
        else {
            editTextPasswdErrorMsg.setText(R.string.pwd_helper)
            editTextPasswdErrorMsg.setTextColor(resources.getColor(R.color.gray_500))
            editTextPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
            editTextPasswd.setPadding(padding,padding,padding,padding)
            return true
        }
    }

    private fun passIsConfirmed() : Boolean {
        editTextConfirmPasswd = findViewById(R.id.TextInputEditText_confirm_passwd)
        editTextConfirmPasswdErrorMsg = findViewById(R.id.tv_no_match_passwd)
        editTextPasswd = findViewById(R.id.TextInputEditText_new_passwd)
        editTextPasswdErrorMsg = findViewById(R.id.tv_reset_invalid_passwd)

        val padding = editTextPasswd.paddingLeft

        val passwd = editTextPasswd.text.toString()
        val secondPasswd = editTextConfirmPasswd.text.toString()
        if(validateNewPasswd()) {
            if(!passwd.equals(secondPasswd)) {
                editTextConfirmPasswdErrorMsg.setText(R.string.no_match_passwd)
                editTextConfirmPasswdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
                editTextConfirmPasswdErrorMsg.isVisible = true
                editTextConfirmPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
                editTextConfirmPasswd.setPadding(padding,padding,padding,padding)
                return false
            }
            else if(secondPasswd.isEmpty()) {
                editTextConfirmPasswdErrorMsg.setText(R.string.empty_field)
                editTextConfirmPasswdErrorMsg.setTextColor(resources.getColor(R.color.red_500))
                editTextConfirmPasswdErrorMsg.isVisible = true
                editTextConfirmPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_red)
                editTextConfirmPasswd.setPadding(padding,padding,padding,padding)
                return false
            }
            else {
                editTextConfirmPasswdErrorMsg.isGone = true
                editTextConfirmPasswd.background = ContextCompat.getDrawable(applicationContext, R.drawable.input_border_normal)
                editTextConfirmPasswd.setPadding(padding,padding,padding,padding)
                return true
            }
        }
        else {
            return false
        }
    }

    private fun sendNewPasswd() {
        confirmBtn = findViewById(R.id.btn_confirm_passwd)

        confirmBtn.setOnClickListener {
            if(!validateNewPasswd() or !passIsConfirmed()){
                Toast.makeText(applicationContext, "Something is wrong!", Toast.LENGTH_LONG).show()
            }
            else {
                Toast.makeText(applicationContext, "Cool, good job!", Toast.LENGTH_LONG).show()
            }
        }
    }
}