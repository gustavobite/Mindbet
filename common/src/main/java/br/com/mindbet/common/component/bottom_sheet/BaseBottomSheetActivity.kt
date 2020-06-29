package br.com.mindbet.common.component.bottom_sheet

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import br.com.mindbet.common.base.BaseActivity


abstract class BaseBottomSheetActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        val window = this.window
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.statusBarColor = Color.TRANSPARENT
        super.onCreate(savedInstanceState)
        setResult(BottomSheetResult.Canceled.resultCode)
    }


    fun finishFlow(bottomSheetResult: BottomSheetResult) {
        setResult(bottomSheetResult.resultCode)
        finish()
    }


}