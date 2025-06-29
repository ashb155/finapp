package com.example.financeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.repository.CurrencyRepository
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CurrencyViewModel : ViewModel() {

    val conversionRate: StateFlow<Double?> = CurrencyRepository.conversionRate
    val isLoading: StateFlow<Boolean> = CurrencyRepository.isLoading
    val error: StateFlow<String?> = CurrencyRepository.error

    fun fetchConversionRate(from: String, to: String) {
        viewModelScope.launch {
            CurrencyRepository.fetchConversionRate(from, to)
        }
    }
}