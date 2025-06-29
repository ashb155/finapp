package com.example.financeapp.ui.theme

import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


private val DarkColorScheme= darkColorScheme(
    primary = Color(0xFF4B7BE5),         //accent
    onPrimary = Color.White,
    primaryContainer = Color(0xFF3356B3), //containers
    secondary = Color(0xFF6B7C93),       //secondary
    onSecondary = Color.White,
    background = Color(0xFF121212),      // True black background
    onBackground = Color(0xFFE1E1E1),    // Soft white text
    surface = Color(0xFF1E1E1E),         // Dark gray surface
    onSurface = Color(0xFFE1E1E1),
    error = Color(0xFFA44859),
    onError =Color(0xFFCF6679)

)

private val AppTypography= Typography()
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