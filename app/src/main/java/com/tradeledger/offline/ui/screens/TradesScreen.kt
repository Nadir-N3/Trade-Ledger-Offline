package com.tradeledger.offline.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tradeledger.offline.model.TradeSide
import com.tradeledger.offline.viewmodel.AppViewModel
import java.time.LocalDate

@Composable
fun TradesScreen(vm: AppViewModel) {
    val trades by vm.tradesWithAssets.collectAsStateWithLifecycle()

    var symbol by remember { mutableStateOf("") }
    var name by remember { mutableStateOf("") }
    var qty by remember { mutableStateOf("") }
    var price by remember { mutableStateOf("") }
    var fee by remember { mutableStateOf("0") }
    var note by remember { mutableStateOf("") }
    var side by remember { mutableStateOf(TradeSide.BUY) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Trades", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Card {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = symbol, onValueChange = { symbol = it.uppercase() }, label = { Text("Symbol (e.g. BTC, AAPL)") })
                OutlinedTextField(value = name, onValueChange = { name = it }, label = { Text("Asset Name") })
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = qty, onValueChange = { qty = it }, label = { Text("Qty") }, modifier = Modifier.weight(1f))
                    OutlinedTextField(value = price, onValueChange = { price = it }, label = { Text("Price") }, modifier = Modifier.weight(1f))
                }
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedTextField(value = fee, onValueChange = { fee = it }, label = { Text("Fee") }, modifier = Modifier.weight(1f))
                    Row {
                        FilterChip(selected = side == TradeSide.BUY, onClick = { side = TradeSide.BUY }, label = { Text("BUY") })
                        Spacer(Modifier.width(8.dp))
                        FilterChip(selected = side == TradeSide.SELL, onClick = { side = TradeSide.SELL }, label = { Text("SELL") })
                    }
                }
                OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Note (optional)") })
                Button(onClick = {
                    val q = qty.toDoubleOrNull() ?: return@Button
                    val p = price.toDoubleOrNull() ?: return@Button
                    val f = fee.toDoubleOrNull() ?: 0.0
                    if (symbol.isBlank() || name.isBlank()) return@Button
                    vm.addAsset(symbol.trim(), name.trim()) { assetId ->
                        vm.addTrade(assetId, LocalDate.now(), side, q, p, f, note.trim())
                    }
                    symbol = ""; name = ""; qty = ""; price = ""; fee = "0"; note = ""
                }) { Text("Add Trade") }
            }
        }
        Divider()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(trades) { tw ->
                ListItem(
                    headlineContent = { Text("${tw.asset.symbol} ${tw.trade.side} ${tw.trade.quantity} @ ${tw.trade.price}") },
                    supportingContent = { Text("Fee: ${tw.trade.fee} | Note: ${tw.trade.note}") }
                )
                Divider()
            }
        }
    }
}