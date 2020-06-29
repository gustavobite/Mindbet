package br.com.mindbet.common.loading

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import br.com.mindbet.common.R
import br.com.mindbet.common.base.BaseActivity
import br.com.mindbet.common.extension.hide
import br.com.mindbet.common.extension.show
import br.com.mindbet.common.extension.then
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable


class CustomProgressBar @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : LinearLayout(context, attrs, defStyleAttr) {

    private var lottie: LottieAnimationView? = null
    private val typedArray: TypedArray = context.theme.obtainStyledAttributes(attrs, R.styleable.CustomProgressBar, defStyleAttr, 0)

    init {
        val size = context.resources.getDimensionPixelSize(R.dimen.loading_size)
        gravity = Gravity.CENTER
        lottie = LottieAnimationView(context)
        lottie?.setAnimation(R.raw.loading_default)
        lottie?.repeatMode = LottieDrawable.RESTART
        lottie?.repeatCount = LottieDrawable.INFINITE
        lottie?.layoutParams = LayoutParams(size, size)
        addView(lottie)
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        when (changedView.visibility) {
            View.VISIBLE -> {
                lottie?.playAnimation()
            }
            View.INVISIBLE, View.GONE -> {
                lottie?.pauseAnimation()
            }
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        when (visibility) {
            View.VISIBLE -> {
                changeStatusBar()
            }
            View.INVISIBLE, View.GONE -> {
                resetStatusBar()
            }
        }
    }

//    private fun chooseAnimation() {
//        if (isGreen()) {
//            lottie?.setAnimation(R.raw.loading_default_green)
//            return
//        }
//        if (isWhite()) {
//            lottie?.setAnimation(R.raw.loading_default_white)
//            return
//        }
//
//        chooseByTheme()
//    }
//
//    private fun chooseByTheme() =
//        isDarkTheme() then lottie?.setAnimation(R.raw.loading_default_white)
//            ?: lottie?.setAnimation(R.raw.loading_default_green)

//    private fun isGreen(): Boolean =
//        typedArray.getBoolean(R.styleable.CustomProgressBar_greenProgress, false)
//
//    private fun isWhite(): Boolean =
//        typedArray.getBoolean(R.styleable.CustomProgressBar_whiteProgress, false)
//
    private fun isOverlay(): Boolean =
        typedArray.getBoolean(R.styleable.CustomProgressBar_isOverlay, false)
//
//    private fun isDarkTheme(): Boolean {
//        val typedValue = TypedValue()
//        context.theme.resolveAttribute(R.attr.loadingAnimationDark, typedValue, true)
//        return typedValue.data != 0  //docs says 0 is false
//    }

    fun showProgress() {
        val alpha = typedArray.getFloat(R.styleable.CustomProgressBar_alphaProgress, 1.0f)
        this.run {
            show()
            setAlpha(alpha)
            bringToFront()
            requestLayout()
        }
    }

    fun hideProgress() = this.hide()
    
    private fun changeStatusBar() {
        if (context is BaseActivity && isOverlay()) {
            val window = (context as? BaseActivity)?.window
            window?.statusBarColor =
                ContextCompat.getColor(context, R.color.blackBackgroundProgress)
        }
    }

    private fun resetStatusBar() {
        if (context is BaseActivity && isOverlay()) {
            val typedValue = TypedValue()
            context.theme.resolveAttribute(R.attr.gradientStart, typedValue, true)
            val window = (context as? BaseActivity)?.window
            window?.statusBarColor = typedValue.data
        }
    }
}
