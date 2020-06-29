package br.com.mindbet.common.helper

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import java.math.BigDecimal
import java.math.RoundingMode
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*


/**
 * Created by Luan on 17/05/17.
 */

class MoneyTextWatcher(private val editText: EditText) : TextWatcher {

    private var current = ""
    override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
        if (s.toString() != current) {
            editText.removeTextChangedListener(this)

            try {
                val replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)

                val cleanString = s.toString().replace(replaceable.toRegex(), "")
                val parsed = java.lang.Double.parseDouble(cleanString)
                val formatted = NumberFormat.getCurrencyInstance(Locale("pt", "BR")).format(parsed / 100)

                current = formatted
                editText.setText(formatted)
                editText.setSelection(formatted.length)
            } catch (ex: Exception) {

            }

            editText.addTextChangedListener(this)
        }
    }

    override fun afterTextChanged(s: Editable) {

    }

    companion object {

        fun toDouble(value: String?): Double {
            value?.let {
                val replaceable = String.format("[%s,.]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)
                val cleanString = value.replace(replaceable.toRegex(), "")
                var parsed = java.lang.Double.parseDouble(cleanString.trim())
                parsed /= 100
                return BigDecimal(parsed).setScale(2, RoundingMode.HALF_UP).toDouble()
            } ?: return 0.0
        }

        fun getCleanCurrencyString(value: String): String {
            val replaceable = String.format("[%s]", NumberFormat.getCurrencyInstance(Locale("pt", "BR")).currency.symbol)
            return value.replace(replaceable.toRegex(), "")
        }

        fun roundOffDecimal(value: String): String? {

            val number = value.toDoubleOrNull()
            number?.let {
                val df = DecimalFormat("#.##")
                df.roundingMode = RoundingMode.CEILING
                return String.format(Locale.ROOT, "%.2f", number)
            } ?: return value
        }

        fun formateToSend(value: String?): String {

            value?.let {
                return it.replace(".", "").replace("R$", "").trim().replace(",", ".")
            } ?: return "0.00"
        }
    }

}