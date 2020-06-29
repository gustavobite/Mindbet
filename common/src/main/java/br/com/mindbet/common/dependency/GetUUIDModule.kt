package br.com.mindbet.common.dependency

import br.com.mindbet.common.BuildConfig
import br.com.mindbet.common.uui.UUIDGeneratorImpl
import br.com.mindbet.common.uui.UUIDGeneratorMock
import org.koin.dsl.module

object GetUUIDModule {
    val dependencyModule = module {
        factory {
            UUIDGeneratorImpl.takeUnless { BuildConfig.IS_MOCK } ?: UUIDGeneratorMock
        }
    }
}
