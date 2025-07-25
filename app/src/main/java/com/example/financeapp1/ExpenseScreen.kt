package com.example.financeapp1

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class Expense(
    val title: String,
    val category: String,
    val amount: Double
)

@Composable
fun ExpenseScreen() {
    val dummyExpenses = listOf(
        Expense("Groceries", "Food", 1200.0),
        Expense("Metro Card", "Travel", 300.0),
        Expense("Netflix", "Entertainment", 499.0),
        Expense("Recharge", "Utilities", 200.0),
    )

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                //add expenses
            }) {
                Icon(Icons.Default.Add, contentDescription = "Add Expense")
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
                modifier=Modifier
                    .align(Alignment.CenterHorizontally)
            )

            Spacer(modifier = Modifier.height(16.dp))

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(dummyExpenses) { expense ->
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
                                    color = Color.LightGray,
                                    style = MaterialTheme.typography.bodySmall
                                )
                            }
                            Text(
                                text = "₹${expense.amount}",
                                color = Color.White,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ExpenseScreenPreview() {
    ExpenseScreen()
}
