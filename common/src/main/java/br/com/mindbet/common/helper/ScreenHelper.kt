package br.com.mindbet.common.helper

import android.content.res.Resources

object ScreenHelper {
	fun dpToPx(dp: Int): Float {
		return (dp * Resources.getSystem().displayMetrics.density)
	}

	fun pxToDp(px: Int): Float {
		return (px / Resources.getSystem().displayMetrics.density)
	}
}