package br.com.mindbet.common.component.error

import android.app.Dialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.annotation.StringRes
import br.com.mindbet.common.R
import kotlinx.android.synthetic.main.error_component.view.*

class ErrorComponent(
    context: Context,
    @StringRes bodyMessage : Int,
    @StringRes buttonText : Int,
    callback: (() -> Unit)? = null
) :
    Dialog(context, R.style.CustomiseDialog) {
    init {
        val layout = LinearLayout(context)
        layout.orientation = LinearLayout.VERTICAL
        val params = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
        )
        val view = LayoutInflater.from(context).inflate(R.layout.error_component, null)
        view.tvMessage.setText(bodyMessage)
        view.buttonAccess.setText(buttonText)

        view.btnClose.setOnClickListener {
            dismiss()
        }

        view.buttonAccess.setOnClickListener {
            callback?.invoke()
            dismiss()
        }

        layout.addView(view, params)
        addContentView(layout, params)
    }
}