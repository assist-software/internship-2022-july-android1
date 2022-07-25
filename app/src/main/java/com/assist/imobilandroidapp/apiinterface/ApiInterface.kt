package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.apiinterface.models.ModifiUserData
import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse
import com.assist.imobilandroidapp.apiinterface.models.SpecificUser
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @POST("api/User/register")
    fun registerUser(
        @Body registerRequest: RegisterRequest
    ): Call<String>

    @POST("api/User/login")
    fun loginUser(
        @Body loginRequest: RegisterRequest
    ): Call<RegisterResponse>

    @GET("api/User/get/{id}")
    fun getUserById(
        @Path("id") id: String?,
        @Header("token") token: String?
    ): Call<SpecificUser>

    @PUT("api/User")
    fun putModifiUserData(
        @Body modifiUserData: ModifiUserData,
        @Header("token") token: String?
    ): Call<String>
}