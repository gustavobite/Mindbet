package br.com.mindbet.core.dependencies

import br.com.mindbet.common.extension.koin.resolveRetrofit
import br.com.mindbet.core.CoreService
import br.com.mindbet.core.CoreServiceMock
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CoreDependencies {

    companion object {
        val dependencies = module {
//
//            //News
//            factory<CoreService> { resolveRetrofit() ?: CoreServiceMock() }
//            single<br.com.news.interactor.GetNews> {
//                br.com.news.interactor.GetNewsImpl(
//                    service = get()
//                )
//            }
//            viewModel { br.com.news.NewsViewModel(get()) }
        }
    }

}