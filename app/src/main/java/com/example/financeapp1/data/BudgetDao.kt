package com.example.financeapp1.data

import androidx.room.*
import com.example.financeapp1.models.BudgetEntity

@Dao
interface BudgetDao{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateBudget(budget:BudgetEntity)

    @Query("SELECT * FROM budget_table WHERE month = :month LIMIT 1")
    suspend fun getBudgetForMonth(month: String): BudgetEntity?
}