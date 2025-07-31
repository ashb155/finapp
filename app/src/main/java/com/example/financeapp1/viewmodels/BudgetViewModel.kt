package com.example.financeapp1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp1.models.BudgetEntity
import com.example.financeapp1.repository.BudgetRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BudgetViewModel(
    private val repository: BudgetRepository
) : ViewModel() {

    private val budgetAmount = MutableStateFlow<Double?>(null)
    val budgetAmount1: StateFlow<Double?> = budgetAmount

    fun loadBudget(month: String) {
        viewModelScope.launch {
            val budget = repository.getBudgetForMonth(month)
            budgetAmount.value = budget?.budgetAmount
        }
    }

    fun setBudget(month: String, amount: Double) {
        viewModelScope.launch {
            val budget = BudgetEntity(month = month, budgetAmount = amount)
            repository.insertOrUpdateBudget(budget)
            budgetAmount.value = amount
        }
    }
}
