package com.example.financeapp.screens

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.example.financeapp.utils.isInternetAvailable
import com.example.financeapp1.data.FavoritePairEntity
import com.example.financeapp.viewmodels.CurrencyViewModel
import com.example.financeapp.viewmodels.CurrencyViewModelFactory
import com.example.financeapp1.data.AppDatabase
import com.example.financeapp1.repository.FavoritePairRepository

@Composable
fun CurrencyScreen(
    navController: NavHostController,
    context: Context = LocalContext.current
) {
    val db = remember { AppDatabase.getDatabase(context) }
    val repository = remember { FavoritePairRepository(db.favoritePairDao()) }
    val viewModel: CurrencyViewModel = viewModel(factory = CurrencyViewModelFactory(repository))

    var fromCurrency by remember { mutableStateOf("USD") }
    var toCurrency by remember { mutableStateOf("INR") }
    var amountInput by remember { mutableStateOf("") }

    val conversionRate by viewModel.conversionRate.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val favorites by viewModel.favorites.collectAsState(initial = emptyList())

    var displayedConversionRate by remember { mutableStateOf<Double?>(null) }
    var displayedFromCurrency by remember { mutableStateOf(fromCurrency) }
    var displayedToCurrency by remember { mutableStateOf(toCurrency) }

    var noInternet by remember { mutableStateOf(false) }

    LaunchedEffect(conversionRate) {
        conversionRate?.let {
            displayedConversionRate = it
            displayedFromCurrency = fromCurrency
            displayedToCurrency = toCurrency
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Spacer(modifier = Modifier.height(30.dp))

        Text(
            "Currency Converter",
            style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.SemiBold),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )

        if (noInternet) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(32.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "No Internet Connection",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.error,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    "Please check your network and try again.",
                    style = MaterialTheme.typography.bodyMedium,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(onClick = {
                    if (isInternetAvailable(context)) {
                        noInternet = false
                    }
                }) {
                    Text("Try Again")
                }
            }
            return@Column
        }

        // Shown only when internet is available
        OutlinedTextField(
            value = amountInput,
            onValueChange = { amountInput = it },
            label = { Text("Amount in $fromCurrency") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedTextField(
                value = fromCurrency,
                onValueChange = { fromCurrency = it.uppercase() },
                label = { Text("From") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
            OutlinedTextField(
                value = toCurrency,
                onValueChange = { toCurrency = it.uppercase() },
                label = { Text("To") },
                singleLine = true,
                modifier = Modifier.weight(1f)
            )
        }

        Button(
            onClick = {
                if ((fromCurrency.isNotBlank() && toCurrency.isNotBlank()) && isInternetAvailable(context)) {
                    viewModel.fetchConversionRate(fromCurrency, toCurrency)
                } else {
                    noInternet = true
                }
            },
            enabled = !isLoading && amountInput.toDoubleOrNull() != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(20.dp),
                    strokeWidth = 2.dp,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            } else {
                Text("Convert")
            }
        }

        error?.let {
            Text(
                text = "Error: $it",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium
            )
        }

        displayedConversionRate?.let { rate ->
            val amount = amountInput.toDoubleOrNull() ?: return@let
            val converted = rate * amount

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f)
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "$amountInput $displayedFromCurrency = %.2f $displayedToCurrency".format(converted),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Medium
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = {
                        viewModel.addFavorite(displayedFromCurrency, displayedToCurrency)
                    }) {
                        Text("Add to Favorites")
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        FavoritePairsList(
            favorites = favorites,
            onSelect = { from, to ->
                fromCurrency = from
                toCurrency = to
            },
            onRemove = { favorite ->
                viewModel.removeFavorite(favorite)
            }
        )
    }
}


@Composable
fun FavoritePairsList(
    favorites: List<FavoritePairEntity>,
    onSelect: (from: String, to: String) -> Unit,
    onRemove: (FavoritePairEntity) -> Unit
) {
    if (favorites.isEmpty()) return

    Column {
        Text(
            "Saved Currency Pairs",
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
        )
        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(favorites) { favorite ->
                Card(
                    modifier = Modifier
                        .clickable { onSelect(favorite.fromCurrency, favorite.toCurrency) }
                        .padding(4.dp),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Text("${favorite.fromCurrency} → ${favorite.toCurrency}")
                        Spacer(modifier = Modifier.width(8.dp))
                        IconButton(onClick = { onRemove(favorite) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Remove Favorite")
                        }
                    }
                }
            }
        }
    }
}
