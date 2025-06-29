package com.example.financeapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class CurrencyResponse(
    val result: String,
    val documentation: String,
    val terms_of_use: String,
    @SerializedName("time_last_update_unix")
    val timeLastUpdateUnix: Long,
    @SerializedName("time_next_update_unix")
    val timeNextUpdateUnix: Long,
    @SerializedName("base_code")
    val baseCode: String,
    @SerializedName("target_code")
    val targetCode: String,
    @SerializedName("conversion_rate")
    val conversionRate: Double
)

