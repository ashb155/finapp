package com.example.financeapp1.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow
//data access object
@Dao
interface FavoritePairDao {

    @Query("SELECT * FROM favorite_pairs")
    fun getAllFavorites(): Flow<List<FavoritePairEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavorite(favorite: FavoritePairEntity)

    @Delete
    suspend fun deleteFavorite(favorite: FavoritePairEntity)
}
