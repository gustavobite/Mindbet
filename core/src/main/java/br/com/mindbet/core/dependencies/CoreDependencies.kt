package br.com.mindbet.core.dependencies

import br.com.mindbet.common.extension.koin.resolveRetrofit
import br.com.mindbet.core.CoreService
import br.com.mindbet.core.CoreServiceMock
import br.com.mindbet.core.simulation.create.data.repository.SimulationRepository
import br.com.mindbet.core.simulation.create.data.repository.SimulationRepositoryImpl
import br.com.mindbet.core.simulation.create.data.service.SimulationService
import br.com.mindbet.core.simulation.create.data.service.SimulationServiceMock
import br.com.mindbet.core.simulation.create.domain.SimulationUseCase
import br.com.mindbet.core.simulation.create.domain.SimulationUseCaseImpl
import br.com.mindbet.core.simulation.create.domain.SimulationValuesUseCase
import br.com.mindbet.core.simulation.create.domain.SimulationValuesUseCaseImpl
import br.com.mindbet.core.simulation.create.presentation.SimulationViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

class CoreDependencies {

    @ExperimentalCoroutinesApi
    companion object {
        val dependencies = module {
//
            single<SimulationService> { resolveRetrofit() ?: SimulationServiceMock() }
            factory<SimulationRepository> {
                SimulationRepositoryImpl(
                    get()
                )
            }
            factory<SimulationValuesUseCase> {
                SimulationValuesUseCaseImpl(
                    get()
                )
            }
            factory<SimulationUseCase> {
                SimulationUseCaseImpl(
                    get()
                )
            }
            viewModel {
                SimulationViewModel(
                    get(),
                    get()
                )
            }
        }
    }

}