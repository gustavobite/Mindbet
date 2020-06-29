package br.com.mindbet.common.base

import android.view.View
import androidx.core.widget.NestedScrollView
import br.com.mindbet.common.extension.isFullyScrolled
import br.com.mindbet.common.extension.isScrolledToTop
import br.com.mindbet.common.gesture.SwipeGestureDetector

abstract class BaseSwipeableFragment : BaseFragment() {

    open var detectSwipeOnRoot : Boolean = true

    override fun onGlobalLayout(root: View) {
        super.onGlobalLayout(root)
        if (detectSwipeOnRoot) {
            setupSwipeDetector(root)
        }
    }

    private val swipeDetector by lazy {
        object : SwipeGestureDetector(this@BaseSwipeableFragment.context!!) {
            override fun onSwipeBottom() {
                onSwipeBottomDetected()
            }
            override fun onSwipeTop() {
                onSwipeTopDetected()
            }
        }
    }

    fun setupSwipeDetector(view: View) {
        if (context != null) {
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
    }

    open fun onSwipeBottomDetected() {
//        this@BaseSwipeableFragment.activity?.onBackPressed()
    }
    open fun onSwipeTopDetected() { }
}