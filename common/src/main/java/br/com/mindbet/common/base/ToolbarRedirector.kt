package br.com.mindbet.common.base

import android.app.Activity
import kotlin.reflect.KClass

interface ToolbarRedirector {
    fun getMenuClassActivity(): KClass<out Activity>
}