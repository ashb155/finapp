package com.example.financeapp1.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "expenses")
data class ExpenseEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val amount: Double,
    val timestamp: Long,
    val date: Long
)

data class ExpenseTotalByDate(
    val date: Long,
    val total: Double
)
