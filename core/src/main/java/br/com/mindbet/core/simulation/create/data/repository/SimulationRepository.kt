package br.com.mindbet.core.simulation.create.data.repository

import br.com.mindbet.common.base.Resource
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import kotlinx.coroutines.flow.Flow

interface SimulationRepository {
    suspend fun getParams(): Flow<Resource<List<SimulationValues>>>
    suspend fun setSimulate(params: SimulationParams): Flow<Resource<Unit>>
}