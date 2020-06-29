package br.com.mindbet.common.extension

import androidx.lifecycle.LiveData
import br.com.mindbet.common.helper.SingleLiveEvent


fun <T> LiveData<T>.toSingleEvent(): LiveData<T> {
    val result = SingleLiveEvent<T>()
    result.addSource(this) {
        result.value = it
    }
    return result
}