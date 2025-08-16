package com.tradeledger.offline.data.dao

import androidx.room.*
import com.tradeledger.offline.data.NoteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes ORDER BY dateEpochDay DESC, id DESC")
    fun observeAll(): Flow<List<NoteEntity>>

    @Insert suspend fun insert(n: NoteEntity): Long
    @Update suspend fun update(n: NoteEntity)
    @Delete suspend fun delete(n: NoteEntity)
}