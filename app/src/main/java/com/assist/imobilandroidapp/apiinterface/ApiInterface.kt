package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiInterface {

    @POST("api/User/register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<String>
}