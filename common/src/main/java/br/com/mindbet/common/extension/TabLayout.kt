package br.com.mindbet.common.extension

import android.view.ViewGroup
import androidx.core.view.children
import com.google.android.material.tabs.TabLayout

fun TabLayout.setMargins(left: Int = 0, top: Int = 0, right: Int = 0, bottom: Int = 0) {
    (this.children.first() as? ViewGroup)?.children?.forEach {
        val layoutParams = it.layoutParams as ViewGroup.MarginLayoutParams
        layoutParams.setMargins(left, top, right, bottom)
        it.requestLayout()
    }
}