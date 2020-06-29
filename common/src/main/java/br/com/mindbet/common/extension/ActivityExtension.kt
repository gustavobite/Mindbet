package br.com.mindbet.common.extension

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Rect
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import java.io.InputStream



fun Activity.showKeyboard() {
    try {
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0)
    } catch (e: Exception) {
        // Ignore exceptions if any
        Log.e("showKeyboard", e.toString(), e)
    }
}

fun Fragment.setKeyboardVisibilityListener(parentView: View, onKeyboardVisibilityListener: OnKeyboardVisibilityListener): ViewTreeObserver.OnGlobalLayoutListener {
//    val parentView = (parentView as ViewGroup).getChildAt(0)
    val listener = object : ViewTreeObserver.OnGlobalLayoutListener {
        private var alreadyOpen: Boolean = false
        private val defaultKeyboardHeightDP = 100
        private val EstimatedKeyboardDP = defaultKeyboardHeightDP + if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) 48 else 0
        private val rect = Rect()

        override fun onGlobalLayout() {
            val estimatedKeyboardHeight = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, EstimatedKeyboardDP.toFloat(), parentView.resources.displayMetrics).toInt()
            parentView.getWindowVisibleDisplayFrame(rect)
            val heightDiff = parentView.rootView.height - (rect.bottom - rect.top)
            val isShown = heightDiff >= estimatedKeyboardHeight

            if (isShown == alreadyOpen) {
                Log.i("Keyboard state", "Ignoring global layout change...")
                return
            }
            alreadyOpen = isShown
            onKeyboardVisibilityListener.onVisibilityChanged(isShown)
        }
    }
    parentView.viewTreeObserver.addOnGlobalLayoutListener(listener)

    return listener
}

@Suppress("DEPRECATION")
@SuppressLint("MissingPermission")
fun Context.vibrate(milli: Long = 500) {
    try {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (vibrator.hasVibrator()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                vibrator.vibrate(VibrationEffect.createOneShot(milli, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                vibrator.vibrate(milli)
            }
        }
    } catch (e: Exception) {
        Log.e("Vibrate ", e.message)
    }
}

interface OnKeyboardVisibilityListener {
    fun onVisibilityChanged(isShow: Boolean)
}

fun Context.readFromJson(name: String): String {
    return try {
        val inputStream: InputStream = assets.open(name)
        inputStream.bufferedReader().use { it.readText() }
    } catch (e: Exception) {
        ""
    }
}


@TargetApi(Build.VERSION_CODES.KITKAT)
fun Activity.transparentStatusBar() {

    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        this.window.statusBarColor = Color.TRANSPARENT
    } else {
        this.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}

@TargetApi(Build.VERSION_CODES.KITKAT)
fun Activity.removeTransparentStatusBar() {

    window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
    } else {
        this.window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
    }
}
