package br.com.mindbet.common.extension

import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView

fun RecyclerView.setOnScrolledFullBottomListener(func: () -> Unit){
    addOnScrollListener(object : RecyclerView.OnScrollListener() {

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)

            if(!recyclerView.canScrollVertically(1) && newState == RecyclerView.SCROLL_STATE_SETTLING){
                func.invoke()
            }
        }

    })
}