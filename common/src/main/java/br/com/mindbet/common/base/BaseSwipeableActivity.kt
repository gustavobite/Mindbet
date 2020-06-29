package br.com.mindbet.common.base

import android.os.Bundle
import android.view.View
import androidx.core.widget.NestedScrollView
import br.com.mindbet.common.extension.isFullyScrolled
import br.com.mindbet.common.extension.isScrolledToTop
import br.com.mindbet.common.gesture.SwipeGestureDetector

abstract class BaseSwipeableActivity : BaseActivity() {
    open var detectSwipeOnRoot : Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (detectSwipeOnRoot) {
            findViewById<View?>(android.R.id.content)?.let { root ->
                setupSwipeDetector(root.rootView)
            }
        }
    }

    private val swipeDetector by lazy {
        object : SwipeGestureDetector(this@BaseSwipeableActivity) {
            override fun onSwipeBottom() {
                onSwipeBottomDetected()
            }
            override fun onSwipeTop() {
                onSwipeTopDetected()
            }
        }
    }

    private fun setupSwipeDetector(view: View) {

        if (view is NestedScrollView) {
            view.setOnTouchListener(swipeDetector)

            view.setOnScrollChangeListener { v: NestedScrollView?, _, _, _, _ ->
                if (view.isFullyScrolled() || view.isScrolledToTop()) {
                    view.setOnTouchListener(swipeDetector)
                }
                else {
                    view.setOnTouchListener(null)
                }
            }
        }
        else {
            view.setOnTouchListener(swipeDetector)
        }

    }

    open fun onSwipeBottomDetected() {
        this@BaseSwipeableActivity.onBackPressed()
    }
    open fun onSwipeTopDetected() {

    }
}