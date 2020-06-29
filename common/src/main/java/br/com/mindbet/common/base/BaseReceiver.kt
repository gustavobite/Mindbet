package br.com.mindbet.common.base

import android.content.Context

interface BaseReceiver {
    fun <T> setup(context: Context, listener: T? = null)
    fun unregisterBroadcast(context: Context)
}