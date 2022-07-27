package com.assist.imobilandroidapp.screens.onboarding.login

import  android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse
import com.assist.imobilandroidapp.databinding.ActivityLogInBinding
import com.assist.imobilandroidapp.screens.averageuser.main.AverageUserActivity
import com.assist.imobilandroidapp.screens.client.main.ClientActivity
import com.assist.imobilandroidapp.screens.onboarding.forgotpassword.ForgotPasswordActivity
import com.assist.imobilandroidapp.screens.onboarding.singup.main.SignUpActivity
import com.assist.imobilandroidapp.storage.SharedPrefManager
import com.assist.imobilandroidapp.utils.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LogInActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        val view = binding.root

        setContentView(view)
        initButtons()
        onLoginBtnClick()
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

        binding.tvSignInAsGuest.setOnClickListener {
            val intent = Intent(this@LogInActivity, AverageUserActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun onLoginBtnClick() {
        binding.btnLogin.setOnClickListener {
            if (!validateEmail() or !validatePassword()
            ) {
                Toast.makeText(this, getString(R.string.invalidPassword), Toast.LENGTH_LONG)
                    .show()
            } else {
                loginUser()
            }
        }
    }

    private fun loginUser() {
        val email = binding.etEmailInput.text.toString()
        val password = binding.etPasswordInput.text.toString()
        val nameDevice = Build.MANUFACTURER + " " + Build.MODEL
        val deviceType = getText(R.string.device_type).toString()
        val location = getText(R.string.location_suceava).toString()
        val status = true

        RetrofitClient.instance.loginUser(
            RegisterRequest(
                email,
                password,
                nameDevice,
                deviceType,
                location,
                status
            )
        ).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.code() == 200) {
                    SharedPrefManager.getInstance().saveToken(response.body())
                    SharedPrefManager.getInstance().saveUserId(response.body())
                    SharedPrefManager.getInstance().saveName(response.body())

                    intent = Intent(this@LogInActivity, ClientActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext,
                        getText(R.string.invalidPassword).toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun validateEmail(): Boolean {
        val validate =
            Validator(binding.etEmailInput, binding.tvErrorEmail, applicationContext, resources)
        return validate.validateEmail()
    }

    private fun validatePassword(): Boolean {
        val validate = Validator(
            binding.etPasswordInput,
            binding.tvErrorPassword,
            applicationContext,
            resources
        )
        return validate.validateLogInPassword()
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance().isLoggedIn) {
            intent = Intent(this@LogInActivity, ClientActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
    }
}