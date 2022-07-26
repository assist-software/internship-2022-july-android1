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
import com.assist.imobilandroidapp.utils.Validator

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

    private fun validateNewPassword(): Boolean {
        val validate = Validator(binding.textInputEditTextNewPasswd, binding.tvResetNewPasswdMsg, applicationContext, resources)
        return validate.validatePassword()
    }

    private fun passwordIsConfirmed(): Boolean {
        val validate = Validator(binding.textInputEditTextConfirmPasswd, binding.tvNoMatchPasswd, applicationContext, resources)
        val newPassword = binding.textInputEditTextNewPasswd.text.toString()
        return validate.validateConfirmPassword(newPassword)
    }


}