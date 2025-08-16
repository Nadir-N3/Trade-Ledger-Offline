package com.tradeledger.offline.data.dao

import androidx.room.*
import com.tradeledger.offline.data.AssetEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface AssetDao {
    @Query("SELECT * FROM assets ORDER BY symbol ASC")
    fun observeAll(): Flow<List<AssetEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(asset: AssetEntity): Long

    @Delete suspend fun delete(asset: AssetEntity)
    @Query("SELECT * FROM assets WHERE id=:id") suspend fun getById(id: Long): AssetEntity?
    @Query("SELECT * FROM assets WHERE symbol=:symbol LIMIT 1") suspend fun findBySymbol(symbol: String): AssetEntity?
}