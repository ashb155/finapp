package com.example.financeapp1.data

import androidx.room.*
import com.example.financeapp1.models.ExpenseEntity
import com.example.financeapp1.models.ExpenseTotalByDate
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expenses ORDER BY timestamp DESC")
    fun getAllExpenses(): Flow<List<ExpenseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExpense(expense: ExpenseEntity)

    @Delete
    suspend fun deleteExpense(expense: ExpenseEntity)

    @Query("SELECT SUM(amount) FROM expenses WHERE timestamp BETWEEN :start AND :end")
    fun getTotalSpentBetween(start: Long, end: Long): Flow<Double?>

    @Query("SELECT date, SUM(amount) as total FROM expenses GROUP BY date ORDER BY date ASC")
    fun getTotalAmountByDate(): Flow<List<ExpenseTotalByDate>>

}
