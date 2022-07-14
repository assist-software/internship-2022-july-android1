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
import com.assist.imobilandroidapp.databinding.ActivityResetPasswordBinding
import com.assist.imobilandroidapp.databinding.ActivitySignUpBinding

class ResetPasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityResetPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityResetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfirmPasswd.setOnClickListener {
            if (!validateNewPassword() or !passwordIsConfirmed()) {
                Toast.makeText(applicationContext, R.string.for_toast_error, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(applicationContext, R.string.for_toast_okay, Toast.LENGTH_LONG).show()
            }
        }
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

    private fun hasDigits(string: String): Boolean {
        for (char in string) {
            if (char.isDigit()) {
                return true
            }
        }
        return false
    }

    private fun validateNewPassword(): Boolean {
        val padding = binding.textInputEditTextNewPasswd.paddingLeft
        val newPasswd = binding.textInputEditTextNewPasswd.text.toString()

        when {
            newPasswd.isEmpty() -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextNewPasswd, binding.tvResetNewPasswdMsg,
                    R.string.empty_field, R.color.red_500, R.drawable.input_border_red, padding
                )
                return false
            }

            newPasswd.length < 9 -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextNewPasswd,
                    binding.tvResetNewPasswdMsg,
                    R.string.insufficient_chars,
                    R.color.red_500,
                    R.drawable.input_border_red,
                    padding
                )
                return false
            }

            !hasDigits(newPasswd) -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextNewPasswd, binding.tvResetNewPasswdMsg,
                    R.string.lacks_digit, R.color.red_500, R.drawable.input_border_red, padding
                )
                return false
            }

            else -> {
                editMessageTextViewsPassword(
                    binding.textInputEditTextNewPasswd, binding.tvResetNewPasswdMsg,
                    R.string.pwd_helper, R.color.gray_500, R.drawable.input_border_normal, padding
                )
                return true
            }
        }
    }

    private fun passwordIsConfirmed(): Boolean {
        val padding = binding.textInputEditTextConfirmPasswd.paddingLeft
        val newPassword = binding.textInputEditTextNewPasswd.text.toString()
        val secondPassword = binding.textInputEditTextConfirmPasswd.text.toString()

        if (!validateNewPassword()) {
            return false
        } else {
            when {
                !newPassword.equals(secondPassword) -> {
                    editMessageTextViewsPassword(
                        binding.textInputEditTextConfirmPasswd,
                        binding.tvNoMatchPasswd,
                        R.string.no_match_passwd,
                        R.color.red_500,
                        R.drawable.input_border_red,
                        padding
                    )
                    binding.tvNoMatchPasswd.isVisible = true
                    return false
                }

                secondPassword.isEmpty() -> {
                    editMessageTextViewsPassword(
                        binding.textInputEditTextConfirmPasswd,
                        binding.tvNoMatchPasswd,
                        R.string.empty_field,
                        R.color.red_500,
                        R.drawable.input_border_red,
                        padding
                    )
                    binding.tvNoMatchPasswd.isVisible = true
                    return false
                }

                else -> {
                    binding.tvNoMatchPasswd.isGone = true
                    binding.textInputEditTextConfirmPasswd.background =
                        ContextCompat.getDrawable(
                            applicationContext,
                            R.drawable.input_border_normal
                        )
                    binding.textInputEditTextConfirmPasswd.setPadding(
                        padding,
                        padding,
                        padding,
                        padding
                    )
                    return true
                }
            }
        }
    }
}