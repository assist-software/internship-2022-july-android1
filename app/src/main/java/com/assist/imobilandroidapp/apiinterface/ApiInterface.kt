package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.apiinterface.models.*
import okhttp3.ResponseBody
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

    @GET("api/Favorite/get/{id}")
    fun getFavoritesListing(
        @Path("id") id: String?,
        @Header("token") token: String?
    ): Call<List<ListingFavoritesFromDB>>

    @POST("api/Favorite/add")
    fun addToFavoritesList(
        @Query("UserId") userIdQuery: String,
        @Query("ListingId") listingIdQuery: String
    ): Call<String>

    @POST("api/User/reset-password")
    fun resetPassword(
        @Body resetPassword: ResetPassword
    ): Call<String>

    @GET("api/Listing/{id}")
    fun viewSingleListing(
        @Path("id") listingId: String
    ): Call<SingleListingResponse>

    @GET("api/User/get/{id}")
    fun getUser(
        @Path("id") userId: String
    )

    @POST("api/Listing/myListings")
    fun getMyListings(
        @Body myListingsRequest: MyListingsRequest,
        @Header("token") token: String?
    ): Call<List<ListingFromDBObject>>

    @DELETE("api/Listing/{id}")
    fun deleteMyListing(
        @Path("id") listingId: String,
        @Header("token") token: String?
    ): Call<ResponseBody>

    @PUT("api/Listing/{id}")
    fun editMyListing(
        @Path("id") listingId: String,
        @Header("token") token: String?,
        @Body postingRequest: UpdateListingRequest,
    ): Call<ResponseBody>

}