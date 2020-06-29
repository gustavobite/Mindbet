package br.com.mindbet.common.component.general

import android.content.Context
import android.util.AttributeSet
import android.widget.RadioGroup
import androidx.appcompat.widget.AppCompatRadioButton


class UncheckableRadioButton : AppCompatRadioButton {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {}

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {}

    override fun toggle() {

        if (isChecked) {
            if (parent != null && parent is RadioGroup) {
                (parent as RadioGroup).clearCheck()
            }
        } else {
            super.toggle()
        }
    }

    override fun getAccessibilityClassName(): CharSequence {
        return UncheckableRadioButton::class.java.name
    }
}