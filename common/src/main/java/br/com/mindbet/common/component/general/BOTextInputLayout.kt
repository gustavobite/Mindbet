package br.com.mindbet.common.component.general

import android.content.Context
import android.graphics.Typeface
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View.OnFocusChangeListener
import com.google.android.material.textfield.TextInputLayout
import android.text.Spanned
import android.text.InputFilter


class BOTextInputLayout(mContext: Context, attrs: AttributeSet) : TextInputLayout(mContext, attrs) {

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()

        this.editText?.apply{
            //0x00000012 == inputType="numberPassword"
            //alteracao feita para quando o campo for do tipo numberPassword ele tenha o olho e um comportamento diferente
            //caso queira fazer o mesmo para outro tipo de password só incrementar o if ou fazer outro
            if(inputType.equals(0x00000012)){
                this@BOTextInputLayout.isEndIconVisible = true
            }else{
                onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
                    this@BOTextInputLayout.isEndIconVisible = hasFocus
                }
                filters = arrayOf(EmojiExcludeFilter())
            }

        }
    }

    override fun setEnabled(enabled: Boolean) {
        isEndIconVisible = enabled
        super.setEnabled(enabled)
    }

    private inner class EmojiExcludeFilter : InputFilter {

        override fun filter(source: CharSequence, start: Int, end: Int, dest: Spanned, dstart: Int, dend: Int): CharSequence? {
            for (i in start until end) {
                val type = Character.getType(source[i])
                if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                    return ""
                }
            }
            return null
        }
    }
}



