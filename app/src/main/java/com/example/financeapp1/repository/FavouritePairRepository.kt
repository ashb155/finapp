package com.example.financeapp1.repository

import com.example.financeapp1.data.FavoritePairDao
import com.example.financeapp1.data.FavoritePairEntity
import kotlinx.coroutines.flow.Flow

class FavoritePairRepository(private val dao: FavoritePairDao) {

    val favorites: Flow<List<FavoritePairEntity>> = dao.getAllFavorites()

    suspend fun addFavorite(from: String, to: String) {
        dao.insertFavorite(FavoritePairEntity(fromCurrency = from, toCurrency = to))
    }

    suspend fun removeFavorite(favorite: FavoritePairEntity) {
        dao.deleteFavorite(favorite)
    }
}
