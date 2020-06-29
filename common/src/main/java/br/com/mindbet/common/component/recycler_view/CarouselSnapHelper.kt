package br.com.mindbet.common.component.recycler_view

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView


class CarouselSnapHelper : LinearSnapHelper() {
    override fun findTargetSnapPosition(
        layoutManager: RecyclerView.LayoutManager?,
        velocityX: Int,
        velocityY: Int
    ): Int {

        if (layoutManager !is RecyclerView.SmoothScroller.ScrollVectorProvider) {
            return RecyclerView.NO_POSITION
        }

        val currentView = findSnapView(layoutManager) ?: return RecyclerView.NO_POSITION

        val myLayoutManager = layoutManager as LinearLayoutManager

        val firstVisibleItemPosition = myLayoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = myLayoutManager.findLastVisibleItemPosition()

        var currentPosition = layoutManager.getPosition(currentView)

        if (velocityX > 400) {
            currentPosition = lastVisibleItemPosition
        } else if (velocityX < 400) {
            currentPosition = firstVisibleItemPosition
        }

        return currentPosition
    }
}