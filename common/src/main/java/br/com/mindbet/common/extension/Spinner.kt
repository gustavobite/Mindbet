package br.com.mindbet.common.extension

import android.view.View
import android.widget.AdapterView
import android.widget.BaseAdapter

fun <T> BaseAdapter.getOnItemSelectedListener(listener: (T) -> Unit): AdapterView.OnItemSelectedListener {

    return object : AdapterView.OnItemSelectedListener {
        override fun onNothingSelected(parent: AdapterView<*>?) {}
        override fun onItemSelected(
            parent: AdapterView<*>?,
            view: View?,
            position: Int,
            id: Long
        ) {
            val selectedItem = getItem(position)
            @Suppress("UNCHECKED_CAST")
            (selectedItem as? T)?.let(listener)
        }

    }

}