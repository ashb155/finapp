package com.example.financeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp.repository.CurrencyRepository
import com.example.financeapp1.data.FavoritePairEntity
import com.example.financeapp1.repository.FavoritePairRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class CurrencyViewModel(
    private val repository: FavoritePairRepository
) : ViewModel() {

    val conversionRate: StateFlow<Double?> = CurrencyRepository.conversionRate
    val isLoading: StateFlow<Boolean> = CurrencyRepository.isLoading
    val error: StateFlow<String?> = CurrencyRepository.error

    val favorites: StateFlow<List<FavoritePairEntity>> = repository.favorites
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    fun fetchConversionRate(from: String, to: String) {
        viewModelScope.launch {
            CurrencyRepository.fetchConversionRate(from, to)
        }
    }

    fun addFavorite(from: String, to: String) {
        viewModelScope.launch {
            repository.addFavorite(from, to)
        }
    }

    fun removeFavorite(favorite: FavoritePairEntity) {
        viewModelScope.launch {
            repository.removeFavorite(favorite)
        }
    }
}
