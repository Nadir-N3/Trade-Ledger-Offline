package com.tradeledger.offline.data

import androidx.room.Embedded
import androidx.room.Relation

data class TradeWithAsset(
    @Embedded val trade: TradeEntity,
    @Relation(parentColumn = "assetId", entityColumn = "id") val asset: AssetEntity
)