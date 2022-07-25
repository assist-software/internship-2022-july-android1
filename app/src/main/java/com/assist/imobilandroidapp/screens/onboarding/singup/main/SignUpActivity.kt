package com.assist.imobilandroidapp.screens.onboarding.singup.main

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.databinding.ActivitySignUpBinding
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity
import com.assist.imobilandroidapp.utils.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        onSignUpBtnClick()
        goToLogIn()
    }

    private fun validateEmail(): Boolean {
        val validate = Validator(
            binding.textInputEditTextEmail,
            binding.tvWrongEmailInput,
            applicationContext,
            resources
        )
        return validate.validateEmail()
    }

    private fun validatePassword(): Boolean {
        val validate = Validator(
            binding.textInputEditTextPasswd,
            binding.tvInvalidPasswd,
            applicationContext,
            resources
        )
        return validate.validatePassword()
    }

    private fun goToLogIn() {
        binding.tvLogIn.setOnClickListener {
            val intent = Intent(this@SignUpActivity, LogInActivity::class.java)
            startActivity(intent)
        }
    }

    private fun registerUser() {
        val email = binding.textInputEditTextEmail.text.toString()
        val password = binding.textInputEditTextPasswd.text.toString()
        val nameDevice = Build.MANUFACTURER + " " + Build.MODEL
        val deviceType = getText(R.string.device_type).toString()
        val location = getText(R.string.location_suceava).toString()
        val status = true

        RetrofitClient.instance.registerUser(
            RegisterRequest(
                email,
                password,
                nameDevice,
                deviceType,
                location,
                status
            )
        ).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(
                        applicationContext,
                        getText(R.string.account_created).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getText(R.string.failed_to_create).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun onSignUpBtnClick() {
        binding.btnSignUp.setOnClickListener {
            if (validateEmail() and validatePassword()) {
                registerUser()
            }
        }
    }
}