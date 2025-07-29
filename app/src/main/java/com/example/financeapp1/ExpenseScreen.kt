package com.example.financeapp1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.financeapp1.repository.ExpenseRepository
import com.example.financeapp1.viewmodels.ExpenseViewModel
import com.example.financeapp1.viewmodels.ExpenseViewModelFactory
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.navigation.NavHostController
import kotlinx.coroutines.CoroutineScope
import java.time.LocalDate


@Composable
fun ExpenseScreen(repository: ExpenseRepository,navController: NavController) {
    val factory = remember { ExpenseViewModelFactory(repository) }
    val viewModel: ExpenseViewModel = viewModel(factory = factory)

    val expenses by viewModel.allExpenses.collectAsState()

    Scaffold(
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                FloatingActionButton(
                    onClick = {
                        navController.navigate("add_expense")
                    }
                ) {
                    Icon(Icons.Default.Add, contentDescription = "Add Expense")
                }
            }
        },
        containerColor = Color(0xFF121212)
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            Text(
                text = "Recent Expenses",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (expenses.isEmpty()) {
                Text(
                    text = "No expenses added yet.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(expenses) { expense ->
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(containerColor = Color.DarkGray)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp),
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Column {
                                    Text(text = expense.title, color = Color.White)
                                    Text(
                                            text = expense.category,
                                            color = MaterialTheme.colorScheme.onPrimary,
                                            style = MaterialTheme.typography.bodySmall
                                        )

                                }
                                Column{
                                Text(
                                    text = "₹${expense.amount}",
                                    color = Color.White,
                                    style = MaterialTheme.typography.titleMedium
                                )
                                    Text(
                                        text=expense.date,
                                        color=MaterialTheme.colorScheme.onPrimary,
                                        style=MaterialTheme.typography.bodySmall
                                    )
                            }}
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AddExpenseScreen(
    repository: ExpenseRepository,
    navController: NavHostController
) {
    val viewModel = remember { ExpenseViewModel(repository) }

    var title by remember { mutableStateOf("") }
    var category by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    val currentDate = remember { LocalDate.now().toString() }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Column {
            Text("Add Expense", style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Title") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = category,
                onValueChange = { category = it },
                label = { Text("Category") },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            OutlinedTextField(
                value = amount,
                onValueChange = { amount = it },
                label = { Text("Amount") },
                modifier = Modifier.fillMaxWidth()
            )
        }
        Spacer(modifier=Modifier.padding(20.dp))

        Button(
            onClick = {
                val amt = amount.toDoubleOrNull()
                if (title.isNotBlank() && category.isNotBlank() && amt != null) {
                    viewModel.addExpense(title, category, amt,currentDate)
                    navController.popBackStack()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            enabled = title.isNotBlank() && category.isNotBlank() && amount.toDoubleOrNull() != null
        ) {
            Text("Add Expense")
        }
        Spacer(modifier=Modifier.padding(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth(),
            contentAlignment = Alignment.Center
        ){
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier
                .height(40.dp),
        ) {
            Text("Cancel")
        }
    }}
}