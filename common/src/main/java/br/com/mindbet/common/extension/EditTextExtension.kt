package br.com.mindbet.common.extension

import android.content.Context
import android.content.Context.INPUT_METHOD_SERVICE
import android.graphics.Typeface
import android.text.*
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.core.content.ContextCompat
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import br.com.mindbet.common.R
import java.text.NumberFormat

fun EditText.focus(openKeyboard: Boolean = true) {
    requestFocus()
    if (openKeyboard) context.showKeyboard(this)
}

fun Context.showKeyboard(view: View) {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun Fragment.hideKeyboard() {
    val imm = context?.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view?.rootView?.windowToken, 0)
}

fun Context.hideKeyboard(view: View) {
    val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(view.windowToken, 0)
}

fun EditText.setMask(mask: String) {
    val watcher = MaskTextWatcher(this, mask)
    this.removeTextChangedListener(watcher)
    this.addTextChangedListener(watcher)
}



fun EditText.setCustomFont(fontPath: String = "font/proximanova_semibold.otf") {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val createFromAsset = Typeface.createFromAsset(context.assets, fontPath);
            val spannable = SpannableString(s).apply {
                setSpan(
                    StyleSpan(createFromAsset.style),
                    0,
                    s?.length ?: 0,
                    Spanned.SPAN_INCLUSIVE_EXCLUSIVE
                )
            }
            this@setCustomFont.removeTextChangedListener(this)
            this@setCustomFont.setText(spannable)
            this@setCustomFont.setSelection(s?.length ?: 0)
            this@setCustomFont.addTextChangedListener(this)
        }
    })
}

fun EditText.addOnTextChange(funcion: (CharSequence?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {}
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            funcion(s)
        }
    })
}

fun EditText.currencyClearString(): String {
    val replaceable = String.format("[%s]", NumberFormat.getCurrencyInstance().currency.symbol)
    return this.text.replace(replaceable.toRegex(), "")
}


