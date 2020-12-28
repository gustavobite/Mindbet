package br.com.mindbet.core.simulation.create.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import kotlinx.coroutines.flow.Flow

abstract class SimulationUseCase : UseCase<SimulationParams,Flow<Resource<Unit>>>()