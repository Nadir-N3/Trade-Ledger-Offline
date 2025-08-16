package com.tradeledger.offline

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.tradeledger.offline.ui.screens.*
import com.tradeledger.offline.ui.theme.TradeLedgerTheme
import com.tradeledger.offline.viewmodel.AppViewModel
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.PieChart
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.Help
import androidx.compose.ui.graphics.vector.ImageVector

class MainActivity : ComponentActivity() {
    private val vm: AppViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TradeLedgerTheme {
                val nav = rememberNavController()
                Scaffold(bottomBar = { BottomBar(nav) }) { padding ->
                    AppNavHost(nav, vm, Modifier.padding(padding))
                }
            }
        }
    }
}

@Composable
fun BottomBar(nav: NavHostController) {
    val items = listOf(
        Dest.Dashboard, Dest.Trades, Dest.Assets, Dest.Cashflows, Dest.Notes, Dest.Help
    )
    NavigationBar {
        val navBackStackEntry by nav.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEach { dest ->
            NavigationBarItem(
                selected = currentRoute == dest.route,
                onClick = { if (currentRoute != dest.route) nav.navigate(dest.route) },
                icon = { Icon(dest.icon, contentDescription = dest.label) },
                label = { Text(dest.label) }
            )
        }
    }
}

object Routes {
    const val Dashboard = "dashboard"
    const val Trades = "trades"
    const val Assets = "assets"
    const val Cashflows = "cashflows"
    const val Notes = "notes"
    const val Help = "help"
}

sealed class Dest(val route: String, val label: String, val icon: ImageVector) {
    object Dashboard : Dest(Routes.Dashboard, "Dashboard", Icons.Filled.Home)
    object Trades : Dest(Routes.Trades, "Trades", Icons.Filled.ShoppingCart)
    object Assets : Dest(Routes.Assets, "Assets", Icons.Filled.PieChart)
    object Cashflows : Dest(Routes.Cashflows, "Cashflow", Icons.Filled.AccountBalanceWallet)
    object Notes : Dest(Routes.Notes, "Notes", Icons.Filled.Edit)
    object Help : Dest(Routes.Help, "Help", Icons.Filled.Help)
}

@Composable
fun AppNavHost(nav: NavHostController, vm: AppViewModel, modifier: Modifier = Modifier) {
    NavHost(navController = nav, startDestination = Routes.Dashboard, modifier = modifier) {
        composable(Routes.Dashboard) { DashboardScreen(vm) }
        composable(Routes.Trades) { TradesScreen(vm) }
        composable(Routes.Assets) { AssetsScreen(vm) }
        composable(Routes.Cashflows) { CashflowsScreen(vm) }
        composable(Routes.Notes) { NotesScreen(vm) }
        composable(Routes.Help) { HelpScreen() }
    }
}