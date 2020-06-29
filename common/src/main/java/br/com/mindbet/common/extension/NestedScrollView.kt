package br.com.mindbet.common.extension

import android.view.View
import androidx.core.widget.NestedScrollView

fun NestedScrollView.setOnScrolledFullBottomListener(func: () -> Unit) {
    this.setOnScrollChangeListener { nested: NestedScrollView?, scrollX: Int, scrollY: Int, oldScrollX: Int, oldScrollY: Int ->
        if (scrollY > oldScrollY){
            if (isFullyScrolled()){
                func.invoke()
            }
        }
    }
}

fun NestedScrollView.isFullyScrolled() : Boolean = scrollY >= (this.getChildAt(0)?.measuredHeight ?: 0) - this.measuredHeight
fun NestedScrollView.isScrolledToTop() : Boolean = scrollY == 0

fun NestedScrollView.scrollFullToTheBottom() {
    post{
        fullScroll(View.FOCUS_DOWN)
    }
}

//scrollY == ((nested?.getChildAt(0)?.measuredHeight ?: 0) -  (nested?.measuredHeight ?: 0))