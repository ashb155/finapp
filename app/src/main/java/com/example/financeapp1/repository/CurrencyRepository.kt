package com.example.financeapp.repository

import com.example.financeapp.api.CurrencyApiService
import com.example.financeapp.api.CurrencyRetrofitInstance
import com.example.financeapp.models.CurrencyResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.withContext

object CurrencyRepository {

    private val apiService: CurrencyApiService = CurrencyRetrofitInstance.api
    private const val apiKey = "e909eec9912569877059e179"

    val conversionRate = MutableStateFlow<Double?>(null)
    val isLoading = MutableStateFlow(false)
    val error = MutableStateFlow<String?>(null)

    suspend fun fetchConversionRate(from: String, to: String) {
        isLoading.value = true
        try {
            val response = withContext(Dispatchers.IO) {
                apiService.getExchangeRate(apiKey, from, to)
            }
            if (response.result == "success") {
                conversionRate.value = response.conversionRate
                error.value = null
            } else {
                conversionRate.value = null
                error.value = "API returned error"
            }
        } catch (e: Exception) {
            conversionRate.value = null
            error.value = e.localizedMessage ?: "Unknown error"
        } finally {
            isLoading.value = false
        }
    }
}