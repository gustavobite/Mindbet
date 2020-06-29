package br.com.mindbet.core.dependencies

import br.com.mindbet.common.extension.koin.resolveRetrofit
import br.com.mindbet.core.CoreService
import br.com.mindbet.core.CoreServiceMock
import br.com.mindbet.core.news.NewsViewModel
import br.com.mindbet.core.news.interactor.GetNews
import br.com.mindbet.core.news.interactor.GetNewsImpl
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class CoreDependencies {

    companion object {
        val dependencies = module {

            //News
            factory<CoreService> { resolveRetrofit() ?: CoreServiceMock() }
            single<GetNews> { GetNewsImpl(service = get()) }
            viewModel { NewsViewModel(get()) }
        }
    }

}