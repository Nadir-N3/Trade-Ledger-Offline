package com.tradeledger.offline.repository

import com.tradeledger.offline.data.*
import com.tradeledger.offline.model.CashFlowType
import com.tradeledger.offline.model.TradeSide
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import java.time.LocalDate

data class PositionSummary(
    val assetId: Long,
    val symbol: String,
    val name: String,
    val quantity: Double,
    val costBasis: Double,
    val avgCost: Double,
    val realizedPnL: Double
)

data class DashboardTotals(
    val totalTrades: Int,
    val openPositions: Int,
    val investedCost: Double,
    val realizedPnL: Double,
    val income: Double,
    val expense: Double,
    val netCash: Double
)

class Repository(private val db: AppDatabase) {

    fun observeTradesWithAssets(): Flow<List<TradeWithAsset>> = db.tradeDao().observeAllWithAssets()
    fun observeAssets(): Flow<List<AssetEntity>> = db.assetDao().observeAll()
    fun observeCashflows(): Flow<List<CashFlowEntity>> = db.cashFlowDao().observeAll()
    fun observeNotes(): Flow<List<NoteEntity>> = db.noteDao().observeAll()

    suspend fun upsertAsset(symbol: String, name: String): Long {
        val existing = db.assetDao().findBySymbol(symbol.uppercase())
        return if (existing != null) existing.id
        else db.assetDao().upsert(AssetEntity(symbol = symbol.uppercase(), name = name))
    }

    suspend fun addTrade(assetId: Long, date: LocalDate, side: TradeSide, qty: Double, price: Double, fee: Double, note: String) {
        db.tradeDao().insert(TradeEntity(assetId = assetId, dateEpochDay = date.toEpochDay(), side = side, quantity = qty, price = price, fee = fee, note = note))
    }

    suspend fun addCashflow(date: LocalDate, type: CashFlowType, amount: Double, category: String, note: String) {
        db.cashFlowDao().insert(CashFlowEntity(dateEpochDay = date.toEpochDay(), type = type, amount = amount, category = category, note = note))
    }

    suspend fun addNote(date: LocalDate, method: String, content: String) {
        db.noteDao().insert(NoteEntity(dateEpochDay = date.toEpochDay(), method = method, content = content))
    }

    fun observePositions(): Flow<List<PositionSummary>> =
        db.assetDao().observeAll().combine(db.tradeDao().observeAll()) { assets, trades ->
            val byAsset = trades.groupBy { it.assetId }
            assets.map { asset ->
                val list = byAsset[asset.id].orEmpty().sortedBy { it.dateEpochDay }
                var qty = 0.0
                var cost = 0.0
                var realized = 0.0
                list.forEach { t ->
                    when (t.side) {
                        TradeSide.BUY -> { cost += t.quantity * t.price + t.fee; qty += t.quantity }
                        TradeSide.SELL -> {
                            val avg = if (qty <= 0.0) 0.0 else cost / qty
                            val removed = avg * t.quantity
                            realized += (t.quantity * t.price - t.fee) - removed
                            cost -= removed; qty -= t.quantity
                        }
                    }
                }
                val avgCost = if (qty <= 0.0) 0.0 else cost / qty
                PositionSummary(asset.id, asset.symbol, asset.name, qty, cost, avgCost, realized)
            }
        }

    fun observeDashboardTotals(): Flow<DashboardTotals> =
        observePositions().combine(observeCashflows()) { pos, cfs ->
            val invested = pos.sumOf { it.costBasis.coerceAtLeast(0.0) }
            val realized = pos.sumOf { it.realizedPnL }
            val income = cfs.filter { it.type == CashFlowType.INCOME }.sumOf { it.amount }
            val expense = cfs.filter { it.type == CashFlowType.EXPENSE }.sumOf { it.amount }
            DashboardTotals(
                totalTrades = 0,
                openPositions = pos.count { it.quantity > 0.0 },
                investedCost = invested,
                realizedPnL = realized,
                income = income,
                expense = expense,
                netCash = income - expense
            )
        }
}