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
import com.tradeledger.offline.model.CashFlowType
import com.tradeledger.offline.viewmodel.AppViewModel
import java.time.LocalDate

@Composable
fun CashflowsScreen(vm: AppViewModel) {
    val cashflows by vm.cashflows.collectAsStateWithLifecycle()

    var amount by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("General") }
    var note by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(CashFlowType.INCOME) }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Cashflows", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Card {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    FilterChip(selected = type == CashFlowType.INCOME, onClick = { type = CashFlowType.INCOME }, label = { Text("Income") })
                    FilterChip(selected = type == CashFlowType.EXPENSE, onClick = { type = CashFlowType.EXPENSE }, label = { Text("Expense") })
                }
                OutlinedTextField(value = amount, onValueChange = { amount = it }, label = { Text("Amount") })
                OutlinedTextField(value = category, onValueChange = { category = it }, label = { Text("Category") })
                OutlinedTextField(value = note, onValueChange = { note = it }, label = { Text("Note") })
                Button(onClick = {
                    val a = amount.toDoubleOrNull() ?: return@Button
                    vm.addCashflow(LocalDate.now(), type, a, category.trim(), note.trim())
                    amount = ""; note = ""
                }) { Text("Add") }
            }
        }
        Divider()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(cashflows) { cf ->
                ListItem(
                    headlineContent = { Text("${cf.type} • ${cf.category}") },
                    supportingContent = { Text("${cf.amount} • Note: ${cf.note}") }
                )
                Divider()
            }
        }
    }
}