package br.com.mindbet.common.extension

fun Float?.valueOrZeroIfNull(): Float{
    return this ?: 0f
}