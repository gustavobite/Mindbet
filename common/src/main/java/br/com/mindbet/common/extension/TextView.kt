package br.com.mindbet.common.extension

import android.graphics.BlurMaskFilter
import android.view.View
import android.widget.TextView
import br.com.mindbet.common.R


fun TextView.setBlur(isBlur: Boolean) {
    setLayerType(View.LAYER_TYPE_SOFTWARE, null)
    val radius = textSize / 3
    val filter = BlurMaskFilter(radius, BlurMaskFilter.Blur.NORMAL).takeIf { isBlur }
    paint.maskFilter = filter
    requestLayout()
}

fun TextView.setState(isHidden: Boolean) {
    if(isHidden) {
        tag = text
        text = context.getString(R.string.hub_balance_hidden_balance)
    }else{
        text = tag.toString()
    }
}