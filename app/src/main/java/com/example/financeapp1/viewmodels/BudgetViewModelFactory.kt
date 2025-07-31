package com.example.financeapp1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.financeapp1.repository.BudgetRepository

class BudgetViewModelFactory(private val repository: BudgetRepository) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BudgetViewModel::class.java)) {
            return BudgetViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
