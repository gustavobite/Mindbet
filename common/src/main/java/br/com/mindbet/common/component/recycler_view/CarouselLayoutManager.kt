package br.com.mindbet.common.component.recycler_view

import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class CarouselLayoutManager : LinearLayoutManager {
    @JvmOverloads
    constructor(
        context: Context?, orientation: Int = RecyclerView.HORIZONTAL,
        reverseLayout: Boolean = false
    ) : super(context, orientation, reverseLayout)

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    )


    var onItemSelectedListener: OnItemSelectedListener<Int> = {}

    override fun onScrollStateChanged(state: Int) {
        super.onScrollStateChanged(state)
        if (state == RecyclerView.SCROLL_STATE_IDLE) {
            val firstItemVisible = findFirstCompletelyVisibleItemPosition()
            if (firstItemVisible != RecyclerView.NO_POSITION)
                onItemSelectedListener(firstItemVisible)
        }
    }

    override fun generateDefaultLayoutParams() =
        scaledLayoutParams(super.generateDefaultLayoutParams())

    override fun generateLayoutParams(lp: ViewGroup.LayoutParams?) =
        scaledLayoutParams(super.generateLayoutParams(lp))

    override fun generateLayoutParams(c: Context?, attrs: AttributeSet?) =
        scaledLayoutParams(super.generateLayoutParams(c, attrs))

    private fun scaledLayoutParams(layoutParams: RecyclerView.LayoutParams) =
        layoutParams.apply {
            if (itemCount > 1)
                when (orientation) {
                    HORIZONTAL -> width = (horizontalSpace * ratio).toInt()
                    VERTICAL -> height = (verticalSpace * ratio).toInt()
                }
        }

    private val horizontalSpace get() = width - paddingStart - paddingEnd

    private val verticalSpace get() = height - paddingTop - paddingBottom

    private val ratio = 0.9f
}