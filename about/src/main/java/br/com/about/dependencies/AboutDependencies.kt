package br.com.about.dependencies

import br.com.about.AboutViewModel
import br.com.about.interactor.GetAbout
import br.com.about.interactor.GetAboutImpl
import br.com.about.interactor.GetMembers
import br.com.about.interactor.GetMembersImpl
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
            single<GetMembers> {
                GetMembersImpl(
                    service = get()
                )
            }
            single<GetAbout> {
                GetAboutImpl(
                    service = get()
                )
            }
            viewModel { AboutViewModel(get(),get()) }
        }
    }

}