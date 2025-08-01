package com.example.financeapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.example.financeapp1.ui.theme.AppTypography


//custom app theme
private val DarkColorScheme= darkColorScheme(
    primary = Color(0xFF4B7BE5),         //accent
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3356B3), //containers
    secondary = Color(0xFFBFD2F1),       //secondary
    onSecondary = Color.White,
    background = Color(0xFF121212),      // True black background
    onBackground = Color(0xFFE1E1E1),    // Soft white text
    surface = Color(0xFF4B4545),         // Dark gray surface
    onSurface = Color(0xFFE1E1E1),
    error = Color(0xFFA44859),
    onError =Color(0xFFCF6679)

)

private val AppShapes=Shapes()

@Composable
fun FinanceAppTheme(content: @Composable () -> Unit){
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = AppTypography,
        shapes= AppShapes,
        content=content
    )
}