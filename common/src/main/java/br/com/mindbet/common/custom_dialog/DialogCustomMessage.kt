package br.com.mindbet.common.custom_dialog

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.ViewGroup
import br.com.mindbet.common.R
import kotlinx.android.synthetic.main.dialog_custom_message.*

class DialogCustomMessage(
    val context: Context?,
    val message: String,
    val onCloseCallback: (() -> Unit)? = null
) {

    fun show() {
        val dialog = Dialog(context!!, android.R.style.Theme_Material_Light_Dialog_NoActionBar)
        dialog.setContentView(R.layout.dialog_custom_message);
        dialog.create()

        dialog.window?.apply {
            setBackgroundDrawableResource(android.R.color.transparent);
            setGravity(Gravity.TOP)
            setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }

        dialog.apply {
            textTitle.text = message
            icClose.setOnClickListener {
                dialog.dismiss()
                onCloseCallback?.invoke()
            }
            setCancelable(false)
            show()
        }
    }
}