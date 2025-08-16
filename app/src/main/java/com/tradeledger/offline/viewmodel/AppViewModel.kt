package com.tradeledger.offline.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.tradeledger.offline.data.AppDatabase
import com.tradeledger.offline.model.CashFlowType
import com.tradeledger.offline.model.TradeSide
import com.tradeledger.offline.repository.DashboardTotals
import com.tradeledger.offline.repository.Repository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.LocalDate

class AppViewModel(app: Application) : AndroidViewModel(app) {
    private val repo = Repository(AppDatabase.get(app))

    val assets = repo.observeAssets().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val tradesWithAssets = repo.observeTradesWithAssets().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val cashflows = repo.observeCashflows().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val notes = repo.observeNotes().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val positions = repo.observePositions().stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val totals: StateFlow<DashboardTotals> =
        combine(repo.observeDashboardTotals(), tradesWithAssets) { t, trades ->
            t.copy(totalTrades = trades.size)
        }.stateIn(viewModelScope, SharingStarted.Eagerly, DashboardTotals(0,0,0.0,0.0,0.0,0.0,0.0))

    fun addAsset(symbol: String, name: String, onDone: (Long) -> Unit = {}) = viewModelScope.launch {
        val id = repo.upsertAsset(symbol, name); onDone(id)
    }

    fun addTrade(assetId: Long, date: LocalDate, side: com.tradeledger.offline.model.TradeSide, qty: Double, price: Double, fee: Double, note: String) =
        viewModelScope.launch { repo.addTrade(assetId, date, side, qty, price, fee, note) }

    fun addCashflow(date: LocalDate, type: com.tradeledger.offline.model.CashFlowType, amount: Double, category: String, note: String) =
        viewModelScope.launch { repo.addCashflow(date, type, amount, category, note) }

    fun addNote(date: LocalDate, method: String, content: String) =
        viewModelScope.launch { repo.addNote(date, method, content) }
}