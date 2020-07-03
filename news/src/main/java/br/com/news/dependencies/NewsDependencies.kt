package br.com.news.dependencies

import br.com.mindbet.common.extension.koin.resolveRetrofit
import br.com.news.NewsService
import br.com.news.NewsServiceMock
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class NewsDependencies {

    companion object {
        val dependencies = module {
//
            //News
            factory<NewsService> { resolveRetrofit() ?: NewsServiceMock() }
            single<br.com.news.interactor.GetNews> {
                br.com.news.interactor.GetNewsImpl(
                    service = get()
                )
            }
            viewModel { br.com.news.NewsViewModel(get()) }
        }
    }

}