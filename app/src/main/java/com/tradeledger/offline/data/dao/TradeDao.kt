package com.tradeledger.offline.data.dao

import androidx.room.*
import com.tradeledger.offline.data.TradeEntity
import com.tradeledger.offline.data.TradeWithAsset
import kotlinx.coroutines.flow.Flow

@Dao
interface TradeDao {
    @Query("SELECT * FROM trades ORDER BY dateEpochDay DESC, id DESC")
    fun observeAll(): Flow<List<TradeEntity>>

    @Transaction
    @Query("SELECT * FROM trades ORDER BY dateEpochDay DESC, id DESC")
    fun observeAllWithAssets(): Flow<List<TradeWithAsset>>

    @Insert suspend fun insert(trade: TradeEntity): Long
    @Update suspend fun update(trade: TradeEntity)
    @Delete suspend fun delete(trade: TradeEntity)
    @Query("SELECT * FROM trades WHERE assetId=:assetId ORDER BY dateEpochDay ASC, id ASC")
    suspend fun getByAsset(assetId: Long): List<TradeEntity>
}