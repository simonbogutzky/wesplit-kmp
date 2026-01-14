package com.simonbogutzky.wesplit

import platform.Foundation.NSLocale
import platform.Foundation.NSNumber
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.currencySymbol
import platform.Foundation.currentLocale

actual fun getCurrencySymbol(): String {
    return NSLocale.currentLocale.currencySymbol
}

actual fun formatCurrency(amount: Double): String {
    val formatter = NSNumberFormatter().apply {
        numberStyle = NSNumberFormatterCurrencyStyle
    }
    return formatter.stringFromNumber(NSNumber(amount)) ?: run {
        // Fallback: create a basic decimal formatter
        val symbol = getCurrencySymbol()
        val basicFormatter = NSNumberFormatter().apply {
            minimumFractionDigits = 2u
            maximumFractionDigits = 2u
        }
        val formattedNumber = basicFormatter.stringFromNumber(NSNumber(amount)) ?: amount.toString()
        "$symbol$formattedNumber"
    }
}