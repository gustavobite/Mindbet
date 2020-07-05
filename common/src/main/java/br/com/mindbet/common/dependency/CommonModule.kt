package br.com.mindbet.common.dependency

import androidx.lifecycle.MutableLiveData
import br.com.mindbet.common.BuildConfig
import br.com.mindbet.common.helper.Event
import br.com.mindbet.common.local.GetLocalUseDataImpl
import br.com.mindbet.common.local.GetLocalUserData
import br.com.mindbet.common.local.SetLocalUseDataImpl
import br.com.mindbet.common.local.SetLocalUserData
import br.com.mindbet.common.user.User
import br.com.mindbet.common.uui.UUIDGeneratorImpl
import br.com.mindbet.common.uui.UUIDGeneratorMock
import org.koin.core.qualifier.named
import org.koin.dsl.module

object CommonModule {

    val USER_QUALIFIER = named("USER_QUALIFIER")

    val dependencyModule = module {

        single(USER_QUALIFIER) { MutableLiveData<Event<User>>() }

        single<GetLocalUserData> {
           GetLocalUseDataImpl(get(USER_QUALIFIER))
        }
        single<SetLocalUserData> {
            SetLocalUseDataImpl(get(USER_QUALIFIER))
        }
    }
}
