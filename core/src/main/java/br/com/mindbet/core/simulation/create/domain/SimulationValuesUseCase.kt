package br.com.mindbet.core.simulation.create.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import kotlinx.coroutines.flow.Flow

abstract class SimulationValuesUseCase : UseCase<Unit,Flow<Resource<List<SimulationValues>>>>()