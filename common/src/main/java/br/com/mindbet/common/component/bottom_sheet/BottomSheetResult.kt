package br.com.mindbet.common.component.bottom_sheet

import android.app.Activity

sealed class BottomSheetResult {
    abstract val resultCode: Int

    object Success : BottomSheetResult() {
        override val resultCode: Int
            get() = Activity.RESULT_OK
    }

    object Failed : BottomSheetResult() {
        override val resultCode: Int
            get() = hashCode()
    }

    object Canceled : BottomSheetResult() {
        override val resultCode: Int
            get() = Activity.RESULT_CANCELED
    }
}