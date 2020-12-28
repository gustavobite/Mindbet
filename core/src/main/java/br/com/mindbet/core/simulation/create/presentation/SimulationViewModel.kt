package br.com.mindbet.core.simulation.create.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.createUUID
import br.com.mindbet.common.extension.toSingleEvent
import br.com.mindbet.common.user.User
import br.com.mindbet.core.simulation.create.data.model.Matches
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import br.com.mindbet.core.simulation.create.domain.SimulationUseCase
import br.com.mindbet.core.simulation.create.domain.SimulationValuesUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect

class SimulationViewModel(
    private val simulationValues: SimulationValuesUseCase,
    private val simulationUseCase: SimulationUseCase
) :ViewModel() {

    private val _simulationResponse = MutableLiveData<Resource<Unit>>()
    val simulationResponse = _simulationResponse.toSingleEvent()

    private val _simulationValuesResponse = MutableLiveData<Resource<List<SimulationValues>>>()
    val simulationValuesResponse = _simulationValuesResponse.toSingleEvent()


    suspend fun getValues(){
        _simulationValuesResponse.postValue(Resource.loading())
        simulationValues(Unit).collect {
            _simulationValuesResponse.postValue(it)
        }
    }

    suspend fun setSimulation(matches1: Matches, matches2: Matches){
        _simulationResponse.postValue(Resource.loading())

        val param = SimulationParams().also {
            it.matches = listOf(matches1,matches2)
            it.id = createUUID(false)
        }

        simulationUseCase(param).collect {
            _simulationResponse.postValue(it)
        }
    }
}