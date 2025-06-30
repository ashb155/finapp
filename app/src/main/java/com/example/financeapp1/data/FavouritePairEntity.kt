package com.example.financeapp1.data

import androidx.room.Entity
import androidx.room.PrimaryKey
//data to be stored
@Entity(tableName = "favorite_pairs")
data class FavoritePairEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val fromCurrency: String,
    val toCurrency: String
)
