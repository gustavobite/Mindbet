package br.com.mindbet.core.simulation.create.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.repository.SimulationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException

@ExperimentalCoroutinesApi
class SimulationUseCaseImpl(private val repository: SimulationRepository) : SimulationUseCase() {
    override suspend fun execute(params: SimulationParams): Flow<Resource<Unit>> {
        return if (params.matches.isNullOrEmpty().not()) repository.setSimulate(params).catch { emit(Resource.error(it)) }
        else flow { Resource.error<Unit>(InvalidParameterException()) }
    }
}