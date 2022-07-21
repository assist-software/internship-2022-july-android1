package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface ApiInterface {

    @POST("api/User/register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<String>

    @POST("api/User/login")
    fun loginUser(
        @Body loginRequest: RegisterRequest
    ): Call<RegisterResponse>
}