package com.tradeledger.offline.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tradeledger.offline.viewmodel.AppViewModel
import java.text.NumberFormat
import java.util.Locale

@Composable
fun AssetsScreen(vm: AppViewModel) {
    val positions by vm.positions.collectAsStateWithLifecycle()
    val nf = NumberFormat.getNumberInstance(Locale.US)
    val cf = NumberFormat.getCurrencyInstance(Locale.US)

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Assets / Positions", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(positions) { p ->
                ElevatedCard {
                    Column(Modifier.padding(12.dp)) {
                        Text("${p.symbol} • ${p.name}", style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(6.dp))
                        Text("Qty: ${nf.format(p.quantity)} • Avg Cost: ${cf.format(p.avgCost)}")
                        Text("Cost Basis: ${cf.format(p.costBasis)} • Realized P&L: ${cf.format(p.realizedPnL)}")
                    }
                }
            }
        }
    }
}