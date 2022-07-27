package com.assist.imobilandroidapp.screens.onboarding.forgotpassword

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.assist.imobilandroidapp.R
import com.assist.imobilandroidapp.apiinterface.RetrofitClient
import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.apiinterface.models.ResetPassword
import com.assist.imobilandroidapp.databinding.ActivityForgotPasswordBinding
import com.assist.imobilandroidapp.screens.onboarding.login.LogInActivity
import com.assist.imobilandroidapp.utils.Validator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordActivity : AppCompatActivity() {

    private lateinit var sendReqBtn: Button
    private lateinit var binding: ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initButtons()
        sendReqForgotPass()
    }

    private fun initButtons() {
        binding.btnSendReq.setOnClickListener {
            Toast.makeText(this, getString(R.string.SendResetLink), Toast.LENGTH_SHORT).show()
        }
        binding.tvBackLogin.setOnClickListener {
            val intent = Intent(this@ForgotPasswordActivity, LogInActivity::class.java)
            this.startActivity(intent)
        }
    }

    private fun sendReqForgotPass() {
        sendReqBtn = findViewById(R.id.btn_send_req)
        binding.btnSendReq.setOnClickListener {
            if (!Validator(
                    binding.etEmailInput,
                    binding.tvErrorEmail,
                    this,
                    resources
                ).validateEmail()
            ) {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.wrong_email),
                    Toast.LENGTH_LONG
                )
                    .show()
            } else {
                Toast.makeText(
                    applicationContext,
                    getString(R.string.correct_email),
                    Toast.LENGTH_LONG
                ).show()
                sendRequestForgotPass()
            }
        }
    }

    private fun sendRequestForgotPass() {
        RetrofitClient.instance.resetPassword(
            ResetPassword(
                binding.etEmailInput.text.toString()
            )
        ).enqueue(object : Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: Response<String>
            ) {
                if (response.code() == 200) {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.email_sent),
                        Toast.LENGTH_LONG
                    ).show()
                } else {
                    Toast.makeText(
                        applicationContext,
                        getString(R.string.unable_to_send_email),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(applicationContext, t.message.toString(), Toast.LENGTH_LONG).show()
            }
        })
    }

}