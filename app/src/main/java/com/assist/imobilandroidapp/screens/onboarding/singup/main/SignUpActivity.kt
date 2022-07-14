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

    private fun editMessageTextViewsEmail(
        editText: EditText,
        textView: TextView,
        msg: Int,
        bool: Boolean,
        border: Int,
        padding: Int
    ) {
        textView.setText(msg)
        textView.isVisible = bool
        editText.background = ContextCompat.getDrawable(applicationContext, border)
        editText.setPadding(padding, padding, padding, padding)
    }

    private fun editMessageTextViewsPassword(
        editText: EditText,
        textView: TextView,
        msg: Int,
        color: Int,
        border: Int,
        padding: Int
    ) {
        textView.setText(msg)
        textView.setTextColor(resources.getColor(color))
        editText.background = ContextCompat.getDrawable(applicationContext, border)
        editText.setPadding(padding, padding, padding, padding)
    }

    private fun validateEmail(): Boolean {
        val padding = binding.textInputEditTextEmail.paddingLeft
        binding.textInputEditTextEmail.setPadding(padding, padding, padding, padding)
        val email = binding.textInputEditTextEmail.text.toString()

        when {
            email.isEmpty() -> {
                editMessageTextViewsEmail(
                    binding.textInputEditTextEmail, binding.tvWrongEmailInput,
                    R.string.empty_field, true, R.drawable.input_border_red, padding
                )
                return false
            }

            !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                editMessageTextViewsEmail(
                    binding.textInputEditTextEmail, binding.tvWrongEmailInput,
                    R.string.wrong_email, true, R.drawable.input_border_red, padding
                )
                return false
            }

            else -> {
                editMessageTextViewsEmail(
                    binding.textInputEditTextEmail, binding.tvWrongEmailInput,
                    R.string.wrong_email, false, R.drawable.input_border_normal, padding
                )
                return true
            }
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

    private fun validatePassword(): Boolean {
        val padding = binding.textInputEditTextEmail.paddingLeft
        val passwd = binding.textInputEditTextPasswd.text.toString()
        binding.textInputEditTextPasswd.setPadding(padding, padding, padding, padding)

        when {
            passwd.isEmpty() -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextPasswd, binding.tvInvalidPasswd,
                    R.string.empty_field, R.color.red_500, R.drawable.input_border_red, padding
                )
                return false
            }

            passwd.length < 9 -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextPasswd,
                    binding.tvInvalidPasswd,
                    R.string.insufficient_chars,
                    R.color.red_500,
                    R.drawable.input_border_red,
                    padding
                )
                return false
            }

            !hasDigits(passwd) -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextPasswd, binding.tvInvalidPasswd,
                    R.string.lacks_digit, R.color.red_500, R.drawable.input_border_red, padding
                )
                return false
            }

            else -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextPasswd, binding.tvInvalidPasswd,
                    R.string.pwd_helper, R.color.gray_500, R.drawable.input_border_normal, padding
                )
                return true
            }
        }
    }
}