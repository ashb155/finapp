package com.example.financeapp.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.financeapp.screens.*
import com.example.financeapp.viewmodels.CurrencyViewModel

@Composable
fun AppNavHost(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "currency") {
        composable("currency") {
                CurrencyScreen(navController = navController)
            }
        }

    }
