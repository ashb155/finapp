package com.example.financeapp1.repository

import com.example.financeapp1.data.ExpenseDao
import com.example.financeapp1.models.ExpenseEntity
import com.example.financeapp1.models.ExpenseTotalByDate
import kotlinx.coroutines.flow.Flow

class ExpenseRepository(private val expenseDao: ExpenseDao) {
    fun getAllExpenses(): Flow<List<ExpenseEntity>> = expenseDao.getAllExpenses()
    suspend fun insertExpense(expense: ExpenseEntity) = expenseDao.insertExpense(expense)
    suspend fun deleteExpense(expense: ExpenseEntity) = expenseDao.deleteExpense(expense)
    fun getTotalAmountByDate(): Flow<List<ExpenseTotalByDate>> = expenseDao.getTotalAmountByDate()
}
