package br.com.mindbet.common.component.general

import android.content.Context
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.widget.AppCompatEditText
import java.text.ParseException
import java.text.SimpleDateFormat
import android.app.Activity
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mindbet.common.R
import br.com.mindbet.common.extension.MaskTextWatcher
import br.com.mindbet.common.extension.toDayMonthYear
import java.util.*
import kotlin.math.min

class RoundedCollapsableButton @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0): AppCompatImageButton(context, attrs, defStyleAttr) {

    init {
        setImageDrawable(ContextCompat.getDrawable(context, R.drawable.ic_expand_less_green_16dp))
    }

    fun changeState(isCollapsed: Boolean){
        val imageDrawable = if(isCollapsed) ContextCompat.getDrawable(context, R.drawable.ic_expand_more_green_16dp) else ContextCompat.getDrawable(context, R.drawable.ic_expand_less_green_16dp)
        setImageDrawable(imageDrawable)
    }

}