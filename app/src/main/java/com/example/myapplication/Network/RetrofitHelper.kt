package com.example.myapplication.Network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitHelper {

    companion object {

//        https://api.github.com/repositories

        private val BASE_URL = "https://api.github.com/"
        private val BASE_URL_NEW= "https://api.github.com/"


        private const val APPLICATION_JSON = "application/json"
        private const val CLIENT_TYPE = "A"

        private fun getOkHttpClient(accessToken: String): OkHttpClient {
            val okHttpClient = OkHttpClient.Builder()
            // okHttpClient.readTimeout(10,TimeUnit.SECONDS)
            //okHttpClient.connectTimeout(20,TimeUnit.SECONDS)
            okHttpClient.connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1, TimeUnit.MINUTES)
                .writeTimeout(1, TimeUnit.MINUTES)
                .addInterceptor(ApiClient.getLoggingInterceptor())
                .cache(null)

            okHttpClient.addInterceptor { chain ->
                val original = chain.request()

                val requestBuilder = original.newBuilder()
                    .header("Accept", APPLICATION_JSON)
                    .header("Content-Type", APPLICATION_JSON)
                    .header("Client-Type", CLIENT_TYPE)

                if (accessToken.isNotEmpty()) {
                    requestBuilder.header("Authorization", accessToken)
                }

                val request = requestBuilder.build()

                chain.proceed(request)
            }

            return okHttpClient.build()
        }


        fun <T> getRetrofit(service: Class<T>, accessToken: String = ""): T {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(getOkHttpClient(accessToken))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(service)
        }

        fun <T> getRetrofit2(service: Class<T>, accessToken: String = ""): T {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL_NEW)
                .client(getOkHttpClient(accessToken))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
            return retrofit.create(service)
        }

    }



}