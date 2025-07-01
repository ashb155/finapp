package com.example.financeapp.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp1.models.ExpenseEntity
import com.example.financeapp1.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.map
import java.util.Calendar

class ExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    val allExpenses: StateFlow<List<ExpenseEntity>> = repository.getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalByDate: StateFlow<Map<Long, Double>> = allExpenses
        .map { expenses ->
            expenses.groupBy { expense ->
                Calendar.getInstance().apply {
                    timeInMillis = expense.date
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
            }.mapValues { (_, group) ->
                group.sumOf { it.amount }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())


    fun addExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.insertExpense(expense)
        }
    }

    fun deleteExpense(expense: ExpenseEntity) {
        viewModelScope.launch {
            repository.deleteExpense(expense)
        }
    }
}
