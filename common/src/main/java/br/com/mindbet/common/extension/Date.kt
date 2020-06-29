package br.com.mindbet.common.extension

import java.text.SimpleDateFormat
import java.util.*

fun Date.toDayMonthYear(): String{
    val sdf = SimpleDateFormat("dd/MM/yyyy")
    return sdf.format(this)
}
