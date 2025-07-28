package com.example.financeapp

import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.room.Room
import com.example.financeapp.screens.CurrencyScreen
import com.example.financeapp1.AddExpenseScreen
import com.example.financeapp1.ExpenseScreen
import com.example.financeapp1.data.AppDatabase
import com.example.financeapp1.repository.ExpenseRepository


@Composable
fun AppNavHost(navController: NavHostController) {
    val context = LocalContext.current
    val db = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        "finance-db"
    ).build()
    val repository = ExpenseRepository(db.expenseDao())

    NavHost(navController = navController, startDestination = "currency") {
        composable("currency") {
            CurrencyScreen(navController = navController)
        }
        composable("expense") {
            ExpenseScreen(repository = repository,navController)
        }
        composable("add_expense"){
            AddExpenseScreen(repository=repository,navController)
        }
    }
}



sealed class BottomNavScreen(
    val route: String,
    val title: String,

) {
    object Currency : BottomNavScreen("currency", "Currency")
    object Expense : BottomNavScreen("expense", "Expense")
}

@Composable
    fun BottomNavigationBar(navController: NavHostController) {
        val screens = listOf(
            BottomNavScreen.Currency,
            BottomNavScreen.Expense
        )

        NavigationBar {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            screens.forEach { screen ->
                NavigationBarItem(
                    icon = {},
                    label = { Text(screen.title) },
                    selected = currentRoute == screen.route,
                    onClick = {
                        if (currentRoute != screen.route) {
                            navController.navigate(screen.route) {
                                popUpTo(navController.graph.startDestinationId) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                )
            }
        }
    }

