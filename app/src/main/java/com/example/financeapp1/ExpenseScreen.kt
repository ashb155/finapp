package com.example.financeapp1

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.example.financeapp1.repository.BudgetRepository
import com.example.financeapp1.repository.ExpenseRepository
import com.example.financeapp1.viewmodels.BudgetViewModel
import com.example.financeapp1.viewmodels.BudgetViewModelFactory
import com.example.financeapp1.viewmodels.ExpenseViewModel
import com.example.financeapp1.viewmodels.ExpenseViewModelFactory
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


@Composable
fun ExpenseScreen(
    repository: ExpenseRepository,
    repository1: BudgetRepository,
    navController: NavController
) {
    val factory = remember { ExpenseViewModelFactory(repository) }
    val factory1 = remember { BudgetViewModelFactory(repository1) }
    val viewModel: ExpenseViewModel = viewModel(factory = factory)
    val budgetViewModel: BudgetViewModel = viewModel(factory = factory1)

    val budget by budgetViewModel.budgetAmount1.collectAsState()
    val expenses by viewModel.allExpenses.collectAsState()
    val currentMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())

    LaunchedEffect(currentMonth) {
        budgetViewModel.loadBudget(currentMonth)
    }



        Scaffold(
        floatingActionButton = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 12.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (budget != null && budget!=0.0) {
                    Text(
                        text = "Monthly Budget: ₹$budget",
                        color = Color.Green,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                } else {
                    Text(
                        text = "No budget set for this month.",
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )
                }

                Row(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    FloatingActionButton(
                        onClick = { navController.navigate("add_expense") },
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                       Text("Add Expense")
                    }

                    FloatingActionButton(
                        onClick = { navController.navigate("add_budget") },
                        modifier = Modifier
                            .width(120.dp)
                            .height(50.dp)
                    ) {
                        Text("Set Budget")
                    }
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
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
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Column(
                                    modifier = Modifier.weight(1f)
                                ) {
                                    Text(text = expense.title, color = Color.White)
                                    Text(
                                        text = expense.category,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                Column(
                                    horizontalAlignment = Alignment.End,
                                    modifier = Modifier.padding(end = 8.dp)
                                ) {
                                    Text(
                                        text = "₹${expense.amount}",
                                        color = Color.White,
                                        style = MaterialTheme.typography.titleMedium
                                    )
                                    Text(
                                        text = expense.date,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }

                                IconButton(
                                    onClick = { viewModel.deleteExpense(expense) },
                                    modifier = Modifier.align(Alignment.CenterVertically)
                                ) {
                                    Icon(
                                        imageVector = Icons.Default.Delete,
                                        contentDescription = "Delete Expense",
                                        tint = MaterialTheme.colorScheme.onPrimary
                                    )
                                }
                            }
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
            Spacer(modifier=Modifier.padding(100.dp))
            Text("Add Expense", style = MaterialTheme.typography.headlineSmall
            ,modifier=Modifier.align(Alignment.CenterHorizontally))
            Spacer(modifier = Modifier.height(16.dp)
                )

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

@Composable
fun AddBudgetScreen(repository: BudgetRepository, navController: NavController) {
    val factory = remember { BudgetViewModelFactory(repository) }
    val budgetViewModel: BudgetViewModel = viewModel(factory = factory)

    var inputBudget by remember { mutableStateOf("") }
    val currentMonth = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier=Modifier.padding(150.dp))
        Text("Set Monthly Budget", style = MaterialTheme.typography.headlineSmall)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = inputBudget,
            onValueChange = { inputBudget = it },
            label = { Text("Budget Amount") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val amount = inputBudget.toDoubleOrNull()
                if (amount != null) {
                    budgetViewModel.setBudget(currentMonth, amount)
                    navController.popBackStack()
                }
            },
            enabled = inputBudget.toDoubleOrNull() != null,
            modifier=Modifier.fillMaxWidth()
                .height(48.dp)
        ) {
            Text("Save Budget")
        }
        Spacer(modifier=Modifier.padding(5.dp))
        Button(
            onClick = {
                    navController.popBackStack()
                }
        ) {
            Text("Cancel")
        }
    }
}

