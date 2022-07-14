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
import com.assist.imobilandroidapp.databinding.ActivitySignUpBinding
import com.assist.imobilandroidapp.utils.Validator
import com.google.android.material.textfield.TextInputEditText

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSignUp.setOnClickListener {
            if (!validateEmail() or !validatePassword()) {
                Toast.makeText(applicationContext, R.string.for_toast_error, Toast.LENGTH_LONG)
                    .show()
            } else {
                Toast.makeText(applicationContext, R.string.for_toast_okay, Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun validateEmail(): Boolean {
        val validate = Validator(binding.textInputEditTextEmail, binding.tvWrongEmailInput, applicationContext, resources)
        return validate.validateEmail()
    }

    private fun validatePassword(): Boolean {
        val validate = Validator(binding.textInputEditTextPasswd, binding.tvInvalidPasswd, applicationContext, resources)
        return validate.validatePassword()
    }
}