package com.assist.imobilandroidapp.screens.onboarding.login

import android.content.Intent
import android.os.Bundle
import android.view.View.inflate
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.databinding.ActivityLogInBinding
import com.assist.imobilandroidapp.screens.main.MainActivity
import com.assist.imobilandroidapp.screens.onboarding.forgotpassword.ForgotPasswordActivity
import com.assist.imobilandroidapp.screens.onboarding.singup.main.SignUpActivity
import com.assist.imobilandroidapp.utils.Validator


class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initButtons()
        loginUser()
    }

    private fun initButtons() {

        binding.btnGoogle.setOnClickListener {
            Toast.makeText(this, getString(R.string.google_button), Toast.LENGTH_SHORT).show()
        }
        binding.tvForgotPass.setOnClickListener {
            val intent = Intent(this@LogInActivity, ForgotPasswordActivity::class.java)
            this.startActivity(intent)
        }
        binding.tvSignUp.setOnClickListener {
            val intent = Intent(this@LogInActivity, SignUpActivity::class.java)
            this.startActivity(intent)
        }
        binding.cbRemember.setOnClickListener {
            Toast.makeText(this, "Check box Button", Toast.LENGTH_SHORT).show()
        }
    }


    private fun loginUser() {
        binding.btnLogin.setOnClickListener {
            if (!validateEmail() or !validatePassword()
            ) {
                Toast.makeText(this, getString(R.string.invalidPassword), Toast.LENGTH_LONG)
                    .show()
            } else {
                intent = Intent(this@LogInActivity, MainActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun validateEmail(): Boolean {
        val validate = Validator(binding.etEmailInput, binding.tvErrorEmail, applicationContext, resources)
        return validate.validateEmail()
    }

    private fun validatePassword(): Boolean {
        val validate = Validator(binding.etPasswordInput, binding.tvErrorPassword, applicationContext, resources)
        return validate.validateLogInPassword()
    }

}