package br.com.mindbet.common.component.carousel

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import br.com.mindbet.common.component.recycler_view.CarouselLayoutManager
import br.com.mindbet.common.component.recycler_view.CarouselSnapHelper
import br.com.mindbet.common.component.recycler_view.OnItemSelectedListener

class CarouselView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : RecyclerView(context, attrs, defStyleAttr) {

    var onItemSelectedListener: OnItemSelectedListener<Int>? = null
    private val carouselLayoutManager = CarouselLayoutManager(context, HORIZONTAL,false).apply {

        onItemSelectedListener = {
            this@CarouselView.onItemSelectedListener?.invoke(it)
        }
    }
    init {
        CarouselSnapHelper().attachToRecyclerView(this)
        layoutManager = carouselLayoutManager
    }
}