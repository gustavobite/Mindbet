package br.com.mindbet.common.interactor

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.lang.Exception
import kotlin.coroutines.CoroutineContext

abstract class ObservableUseCase<in T, O> : CoroutineScope, KoinComponent {

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO

    abstract fun execute(params: T) : LiveData<O>

    operator fun invoke(params: T) : LiveData<O> {
        return execute(params)
    }

}