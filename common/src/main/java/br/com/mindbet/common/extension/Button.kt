package br.com.mindbet.common.extension

import android.graphics.drawable.Drawable
import android.widget.Button

fun Button.changeStateStyle(drawable: Drawable?,textColor : Int?){
    drawable?.let {
        background = drawable
    }
    textColor?.let {
        setTextColor(it)
    }
}