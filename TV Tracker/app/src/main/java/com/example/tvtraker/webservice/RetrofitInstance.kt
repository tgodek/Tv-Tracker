package com.example.tvtraker.webservice

import com.example.tvtraker.BuildConfig
import com.example.tvtraker.webservice.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(OkHttpClient.Builder().also { client ->
                if(BuildConfig.DEBUG){
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    client.addInterceptor(logging)
                }
            }.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: SerijeApi by lazy {
        retrofit.create(SerijeApi::class.java)
    }
}