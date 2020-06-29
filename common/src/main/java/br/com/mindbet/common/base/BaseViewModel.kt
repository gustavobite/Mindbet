package br.com.mindbet.common.base

import android.app.Activity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mindbet.common.helper.Event
import kotlin.reflect.KClass

class BaseViewModel(private val redirector: ToolbarRedirector) : ViewModel(){

    fun getMenuActivity(): KClass<out Activity>{
        return redirector.getMenuClassActivity()
    }

}