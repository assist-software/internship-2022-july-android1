package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.apiinterface.models.ListingFromDBObject
import com.assist.imobilandroidapp.apiinterface.models.PostListing
import com.assist.imobilandroidapp.apiinterface.models.RegisterRequest
import com.assist.imobilandroidapp.apiinterface.models.RegisterResponse
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

    @GET("api/Listing")
    fun searchForListing(
        @Query("search") searchQuery: String,
        //@Header("token") token: String?
    ): Call<List<ListingFromDBObject>>

    @GET("api/Listing")
    fun getListings(): Call<List<ListingFromDBObject>>

    @POST("api/Listing/Create")
    fun addListing(
        @Body postingRequest: PostListing,
        @Header("token") token: String?
    ): Call<String>
}