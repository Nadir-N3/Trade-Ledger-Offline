package com.tradeledger.offline.data.dao

import androidx.room.*
import com.tradeledger.offline.data.CashFlowEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CashFlowDao {
    @Query("SELECT * FROM cashflows ORDER BY dateEpochDay DESC, id DESC")
    fun observeAll(): Flow<List<CashFlowEntity>>

    @Insert suspend fun insert(cf: CashFlowEntity): Long
    @Update suspend fun update(cf: CashFlowEntity)
    @Delete suspend fun delete(cf: CashFlowEntity)
}