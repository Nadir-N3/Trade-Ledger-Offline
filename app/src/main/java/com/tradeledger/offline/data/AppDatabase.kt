package com.tradeledger.offline.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.tradeledger.offline.data.dao.AssetDao
import com.tradeledger.offline.data.dao.CashFlowDao
import com.tradeledger.offline.data.dao.NoteDao
import com.tradeledger.offline.data.dao.TradeDao

@Database(
    entities = [AssetEntity::class, TradeEntity::class, CashFlowEntity::class, NoteEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun assetDao(): AssetDao
    abstract fun tradeDao(): TradeDao
    abstract fun cashFlowDao(): CashFlowDao
    abstract fun noteDao(): NoteDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null
        fun get(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "trade_ledger.db"
                ).build().also { INSTANCE = it }
            }
    }
}