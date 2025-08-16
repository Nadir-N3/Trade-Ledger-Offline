package com.tradeledger.offline.ui.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun HelpScreen() {
    val context = LocalContext.current
    val clipboard = LocalClipboardManager.current
    val ig = "https://instagram.com/__naadiir.fx"

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text("Help & Feedback", style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        Text("Kalau ada saran, bug report, atau mau request fitur, DM Instagram berikut.")

        ElevatedCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                Text("Instagram: __naadiir.fx", style = MaterialTheme.typography.titleMedium)
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(ig))
                        context.startActivity(intent)
                    }) { Text("Buka Instagram") }
                    OutlinedButton(onClick = { clipboard.setText(AnnotatedString(ig)) }) { Text("Copy Link") }
                }
            }
        }
        Text("Tautan ini akan membuka browser atau app Instagram (jika terpasang).")
    }
}