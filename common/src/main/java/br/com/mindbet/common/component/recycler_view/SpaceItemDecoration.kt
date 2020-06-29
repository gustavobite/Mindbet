package br.com.mindbet.common.component.recycler_view

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import br.com.mindbet.common.extension.dp


class SpaceItemDecoration(val spaceDP: Int = 16, val orientation: Int = RecyclerView.VERTICAL) :
    RecyclerView.ItemDecoration() {
    override fun getItemOffsets(
        outRect: Rect, view: View, parent: RecyclerView,
        state: RecyclerView.State
    ) {

        if (parent.getChildAdapterPosition(view) != parent.adapter?.itemCount?.minus(1)) {
            if (orientation == RecyclerView.VERTICAL) outRect.bottom = view.context.dp(spaceDP)
            else outRect.right = view.context.dp(spaceDP)
        }
    }
}