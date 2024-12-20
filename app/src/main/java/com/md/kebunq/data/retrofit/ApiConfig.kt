package com.md.kebunq.data.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiConfig {
    companion object {
        fun getApiService(): ApiService {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            }
            
            val client = OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .retryOnConnectionFailure(true)
                .build()
                
            val retrofit = Retrofit.Builder()
                .baseUrl("https://kebunq-backend-api-269618306064.us-central1.run.app/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                
            return retrofit.create(ApiService::class.java)
        }
    }
}