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
import com.tradeledger.offline.viewmodel.AppViewModel
import java.time.LocalDate

@Composable
fun NotesScreen(vm: AppViewModel) {
    val notes by vm.notes.collectAsStateWithLifecycle()
    var method by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(Modifier.fillMaxSize().padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
        Text("Notes (Trading Methods)", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Card {
            Column(Modifier.padding(12.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(value = method, onValueChange = { method = it }, label = { Text("Method / Strategy name") })
                OutlinedTextField(value = content, onValueChange = { content = it }, label = { Text("Notes") }, modifier = Modifier.fillMaxWidth().height(120.dp))
                Button(onClick = {
                    if (content.isBlank()) return@Button
                    vm.addNote(LocalDate.now(), method.trim(), content.trim())
                    method = ""; content = ""
                }) { Text("Save Note") }
            }
        }
        Divider()
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(notes) { n ->
                ListItem(headlineContent = { Text(n.method.ifBlank { "Note" }) }, supportingContent = { Text(n.content) })
                Divider()
            }
        }
    }
}