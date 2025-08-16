package com.tradeledger.offline.data

import androidx.room.TypeConverter
import com.tradeledger.offline.model.AssetType
import com.tradeledger.offline.model.CashFlowType
import com.tradeledger.offline.model.TradeSide

class Converters {
    @TypeConverter fun fromTradeSide(value: TradeSide): String = value.name
    @TypeConverter fun toTradeSide(value: String): TradeSide = TradeSide.valueOf(value)

    @TypeConverter fun fromCFType(value: CashFlowType): String = value.name
    @TypeConverter fun toCFType(value: String): CashFlowType = CashFlowType.valueOf(value)

    @TypeConverter fun fromAssetType(value: AssetType): String = value.name
    @TypeConverter fun toAssetType(value: String): AssetType = AssetType.valueOf(value)
}