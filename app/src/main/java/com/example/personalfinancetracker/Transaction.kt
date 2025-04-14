package com.example.personalfinancetracker
import java.util.Date
import android.content.Context
import android.content.SharedPreferences

data class Transaction (
    val title: String,
    val amount: Double,
    val category: String,
    val date: Date
)