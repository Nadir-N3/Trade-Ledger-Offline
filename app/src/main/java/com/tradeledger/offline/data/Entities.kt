package com.tradeledger.offline.data

import androidx.room.*
import com.tradeledger.offline.model.AssetType
import com.tradeledger.offline.model.CashFlowType
import com.tradeledger.offline.model.TradeSide

@Entity(tableName = "assets")
data class AssetEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val symbol: String,
    val name: String,
    val type: AssetType = AssetType.OTHER,
    val currency: String = "USD"
)

@Entity(
    tableName = "trades",
    foreignKeys = [ForeignKey(
        entity = AssetEntity::class,
        parentColumns = ["id"],
        childColumns = ["assetId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("assetId")]
)
data class TradeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val assetId: Long,
    val dateEpochDay: Long, // LocalDate.toEpochDay()
    val side: TradeSide,
    val quantity: Double,
    val price: Double,
    val fee: Double = 0.0,
    val note: String = ""
)

@Entity(tableName = "cashflows")
data class CashFlowEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateEpochDay: Long,
    val type: CashFlowType,
    val amount: Double,
    val category: String = "General",
    val note: String = ""
)

@Entity(tableName = "notes")
data class NoteEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val dateEpochDay: Long,
    val method: String = "",
    val content: String = ""
)