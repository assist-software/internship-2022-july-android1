package com.assist.imobilandroidapp.apiinterface

import com.assist.imobilandroidapp.storage.SharedPrefManager
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {

    //private val AUTH = Base64.encodeToString("emailcorect:parolacorecta01".toByteArray(), Base64.NO_WRAP)
    private val token = SharedPrefManager.getInstance().fetchToken()
    private val okHttpClient = OkHttpClient.Builder().addInterceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder().addHeader("Authorization","Bearer "+ token)
            .method(original.method(), original.body())
        val request = requestBuilder.build()
        chain.proceed(request)
    }.build()

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