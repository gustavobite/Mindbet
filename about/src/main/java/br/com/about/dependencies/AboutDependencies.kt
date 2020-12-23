package br.com.about.dependencies

import br.com.about.AboutViewModel
import br.com.about.domain.GetAbout
import br.com.about.domain.GetAboutImpl
import br.com.about.domain.GetMembers
import br.com.about.domain.GetMembersImpl
import br.com.about.repository.AboutRepository
import br.com.about.repository.AboutRepositoryImpl
import br.com.about.service.AboutService
import br.com.about.service.AboutServiceMock
import br.com.mindbet.common.extension.koin.resolveRetrofit
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class AboutDependencies {

    companion object {
        val dependencies = module {
            factory<AboutService> { resolveRetrofit() ?: AboutServiceMock(context = androidContext()) }
            factory<AboutRepository> { AboutRepositoryImpl(get()) }
            single<GetMembers> {
                GetMembersImpl(get())
            }
            single<GetAbout> {
                GetAboutImpl(get())
            }
            viewModel { AboutViewModel(get(),get()) }
        }
    }

}