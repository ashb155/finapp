package com.example.financeapp1.repository

import com.example.financeapp1.data.BudgetDao
import com.example.financeapp1.models.BudgetEntity

class BudgetRepository(private val budgetDao: BudgetDao) {

    suspend fun insertOrUpdateBudget(budget: BudgetEntity) {
        budgetDao.insertOrUpdateBudget(budget)
    }

    suspend fun getBudgetForMonth(month: String): BudgetEntity? {
        return budgetDao.getBudgetForMonth(month)
    }
}
