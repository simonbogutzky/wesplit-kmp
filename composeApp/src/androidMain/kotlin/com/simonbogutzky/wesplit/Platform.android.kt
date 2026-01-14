package com.simonbogutzky.wesplit

import java.text.NumberFormat
import java.util.Currency
import java.util.Locale

actual fun getCurrencySymbol(): String {
    return try {
        Currency.getInstance(Locale.getDefault()).symbol
    } catch (_: Exception) {
        "$" // Fallback to USD symbol if currency cannot be determined
    }
}

actual fun formatCurrency(amount: Double): String {
    return try {
        NumberFormat.getCurrencyInstance(Locale.getDefault()).format(amount)
    } catch (_: Exception) {
        "$${"%.2f".format(amount)}" // Fallback to USD format
    }
}