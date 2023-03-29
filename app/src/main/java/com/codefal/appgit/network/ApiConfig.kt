package com.codefal.appgit.network

import com.codefal.appgit.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiConfig {
    private const val BASE_URL = "https://api.github.com/"
    private val TOKEN = BuildConfig.KEY

    private val authInterceptor = Interceptor { chain ->
        val req = chain.request()
        val requestHeaders = req.newBuilder()
            .addHeader("Authorization", TOKEN)
            .build()
        chain.proceed(requestHeaders)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val instance : ApiService by lazy {
        val service = Retrofit.Builder().addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(client)
            .build()
        service.create(ApiService::class.java)
    }
}