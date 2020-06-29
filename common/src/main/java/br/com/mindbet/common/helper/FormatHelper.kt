package br.com.mindbet.common.helper
import java.text.DecimalFormat
import java.text.NumberFormat

class FormatHelper {
    companion object {
        fun toCurrency(value: Long) : String {
            val formatter = NumberFormat.getCurrencyInstance()
            return formatter.format(value / 100.0)
        }

        fun fromCurrency(str: String) : Long? {
            val numberOnly = str.replace("[^\\d]".toRegex(), "")
            return numberOnly.toLongOrNull()
        }

        fun getCurrencySymbol() : String {
            val formatter = NumberFormat.getCurrencyInstance()
            return formatter.currency.symbol
        }
    }
}