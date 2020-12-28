package br.com.mindbet.core.simulation.create.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import br.com.mindbet.core.simulation.create.data.repository.SimulationRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException

@ExperimentalCoroutinesApi
class SimulationValuesUseCaseImpl(private val repository: SimulationRepository) : SimulationValuesUseCase() {
    override suspend fun execute(params: Unit): Flow<Resource<List<SimulationValues>>> = repository.getParams().catch { emit(Resource.error(it)) }
}