package br.com.news.dependencies

import br.com.mindbet.common.extension.koin.resolveRetrofit
import br.com.news.data.service.NewsService
import br.com.news.data.service.NewsServiceMock
import br.com.news.data.repository.NewsRepository
import br.com.news.data.repository.NewsRepositoryImpl
import br.com.news.domain.GetNewsUseCase
import br.com.news.domain.GetNewsUseCaseImpl
import br.com.news.presentation.NewsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class NewsDependencies {

    companion object {
        val dependencies = module {
//
            //News
            factory<NewsService> { resolveRetrofit() ?: NewsServiceMock() }
            factory<NewsRepository> { NewsRepositoryImpl(get()) }
            single<GetNewsUseCase> {
                GetNewsUseCaseImpl(
                    repository = get()
                )
            }
            viewModel { NewsViewModel(get()) }
        }
    }

}