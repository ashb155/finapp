package com.example.financeapp1

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.material3.Typography
import androidx.compose.ui.graphics.SolidColor
import ir.ehsannarmani.compose_charts.models.LabelProperties

@Composable
fun ExpenseScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        ColumnChart(
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp),
            data = listOf(
                Bars(
                    label = "Jan",
                    values = listOf(
                        Bars.Data(label = "Linux", value = 50.0, color = SolidColor(Color.Yellow)),
                    )
                ),
                Bars(
                        label = "March",
                values = listOf(
                    Bars.Data(label = "Apple", value = 80.0, color = SolidColor(Color.White))
                )
            )),
            barProperties = BarProperties(
                spacing = 1.dp,


            ),
            labelProperties= LabelProperties(
                enabled=true,
                textStyle = MaterialTheme.typography.titleMedium,
                labels = listOf("Apr","Mar"),
                builder = {modifier,label,shouldRotate,index->
                Text(modifier=modifier,text=label)}

            ),


            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            ),
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(text = "Expense Tracker Placeholder - Pending", style = MaterialTheme.typography.bodyMedium,
            color = Color.White)
    }
}

