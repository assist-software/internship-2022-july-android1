package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.storage.SharedPrefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    private val token = SharedPrefManager.getInstance().fetchToken()
    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder().addHeader("Authorization", "Bearer $token")
            .method(original.method(), original.body())
        val request = requestBuilder.build()
        chain.proceed(request)
    }.writeTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).build()

    private const val BASE_URL = "http://assist-jully-2022-be1.azurewebsites.net/"

    val instance: ApiInterface by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()

        retrofit.create(ApiInterface::class.java)
    }
}