package br.com.mindbet.common.component.bottom_sheet


import android.app.Activity
import android.app.Dialog
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import br.com.mindbet.common.R

/**
 * Created by Luan on 15/04/19.
 */


class BottomMessage(val activity: Activity) {

    companion object {
        val SIZE_MATCH_PARENT = "SIZE_MATCH_PARENT"
        val SIZE_WRAP_CONTENT = "SIZE_WRAP_CONTENT"
    }

    lateinit var mBottomSheetDialog: Dialog

    fun build(resource: Int, size: String = SIZE_WRAP_CONTENT, background:Int? = null, isCancelable:Boolean = false, callback: (View, Dialog) -> Unit){
        val view = activity.layoutInflater.inflate(resource, null)

        mBottomSheetDialog = Dialog(activity, R.style.MaterialDialogSheet)
        mBottomSheetDialog.setContentView(view)
        mBottomSheetDialog.setCancelable(isCancelable)

        when(size){
            SIZE_WRAP_CONTENT -> {
                mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            }

            SIZE_MATCH_PARENT -> {
                mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT)
            }

            else -> {
                if (size.toInt() > 400)
                    mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, size.toInt())
                else
                    mBottomSheetDialog.window!!.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
            }
        }

        background?.let {   mBottomSheetDialog.window?.setBackgroundDrawableResource(background) }

        mBottomSheetDialog.window!!.setGravity(Gravity.BOTTOM)
        mBottomSheetDialog.show()

        callback(view,mBottomSheetDialog)

    }
}