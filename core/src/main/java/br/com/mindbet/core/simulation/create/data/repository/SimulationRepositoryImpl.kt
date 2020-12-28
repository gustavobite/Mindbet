package br.com.mindbet.core.simulation.create.data.repository

import br.com.mindbet.common.base.Resource
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import br.com.mindbet.core.simulation.create.data.service.SimulationService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException

class SimulationRepositoryImpl(private val service: SimulationService) :
    SimulationRepository {
    override suspend fun getParams(): Flow<Resource<List<SimulationValues>>> = flow {
        val response = service.getParams()
        if (response.isSuccessful) emit(Resource.success(response.body()))
        else emit(Resource.error(InvalidParameterException()))
    }

    override suspend fun setSimulate(params: SimulationParams): Flow<Resource<Unit>> = flow{
        val response = service.setSimulate(params)
        if (response.isSuccessful) emit(Resource.success(response.body()))
        else emit(Resource.error(InvalidParameterException()))
    }
}