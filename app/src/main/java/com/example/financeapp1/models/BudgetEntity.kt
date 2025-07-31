package com.example.financeapp1.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "budget_table")
data class BudgetEntity(
    @PrimaryKey(autoGenerate = false)
    val month: String,
    val budgetAmount: Double
)