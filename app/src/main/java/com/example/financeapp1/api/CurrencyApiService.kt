package com.example.financeapp.api

import com.example.financeapp.models.CurrencyCodesResponse
import com.example.financeapp.models.CurrencyResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface CurrencyApiService {
    @GET("v6/{apiKey}/pair/{from}/{to}")
    suspend fun getExchangeRate(
        @Path("apiKey") apiKey: String,
        @Path("from") fromCurrency: String,
        @Path("to") toCurrency: String
    ): CurrencyResponse

    @GET("v6/{apiKey}/codes")
    suspend fun getCurrencyCodes(
        @Path("apiKey") apiKey: String
    ): CurrencyCodesResponse
}
