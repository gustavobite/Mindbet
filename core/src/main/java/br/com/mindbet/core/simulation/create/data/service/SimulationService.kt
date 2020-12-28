package br.com.mindbet.core.simulation.create.data.service

import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface SimulationService {

    suspend fun getParams(): Response<List<SimulationValues>>
    suspend fun setSimulate(params: SimulationParams): Response<Unit>
}