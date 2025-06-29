package com.example.financeapp.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CurrencyRetrofitInstance {
    private const val BASE_URL = "https://v6.exchangerate-api.com/"

    val api: CurrencyApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(CurrencyApiService::class.java)
    }
}