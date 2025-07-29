package com.example.financeapp1.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.financeapp1.models.ExpenseEntity
import com.example.financeapp1.repository.ExpenseRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.Calendar

class ExpenseViewModel(
    private val repository: ExpenseRepository
) : ViewModel() {

    val allExpenses: StateFlow<List<ExpenseEntity>> = repository.getAllExpenses()
        .stateIn(viewModelScope, SharingStarted.Lazily, emptyList())

    val totalByDay: StateFlow<Map<Long, Double>> = allExpenses
        .map { expenses ->
            expenses.groupBy { expense ->
                Calendar.getInstance().apply {
                    timeInMillis = expense.timestamp
                    set(Calendar.HOUR_OF_DAY, 0)
                    set(Calendar.MINUTE, 0)
                    set(Calendar.SECOND, 0)
                    set(Calendar.MILLISECOND, 0)
                }.timeInMillis
            }.mapValues { (_, group) ->
                group.sumOf { it.amount }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, emptyMap())

    fun addExpense(title: String, category: String, amount: Double,date:String ) {
        val timestamp = System.currentTimeMillis()
        val expense = ExpenseEntity(
            title = title,
            category = category,
            amount = amount,
            timestamp = timestamp,
            date=date
        )
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
