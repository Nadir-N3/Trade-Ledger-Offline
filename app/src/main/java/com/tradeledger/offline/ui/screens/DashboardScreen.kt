package com.tradeledger.offline.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.tradeledger.offline.viewmodel.AppViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.text.NumberFormat
import java.util.Locale

@Composable
fun DashboardScreen(vm: AppViewModel) {
    val totals by vm.totals.collectAsStateWithLifecycle()
    val nf = NumberFormat.getNumberInstance(Locale.US)
    val cf = NumberFormat.getCurrencyInstance(Locale.US)

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Dashboard", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Total Trades", nf.format(totals.totalTrades))
            StatCard("Open Positions", nf.format(totals.openPositions))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Invested Cost", cf.format(totals.investedCost))
            StatCard("Realized P&L", cf.format(totals.realizedPnL))
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
            StatCard("Income", cf.format(totals.income))
            StatCard("Expense", cf.format(totals.expense))
            StatCard("Net Cash", cf.format(totals.netCash))
        }
        Spacer(Modifier.height(8.dp))
        Text("Semua data offline (Room). Tambahkan trade, cashflow, dan catatan dari tab bawah.", style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
private fun StatCard(title: String, value: String) {
    Card(modifier = Modifier.weight(1f), elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)) {
        Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.Start) {
            Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Spacer(Modifier.height(6.dp))
            Text(value, style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
        }
    }
}