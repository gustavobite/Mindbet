package br.com.mindbet.core.simulation.create.data.service

import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class SimulationServiceMock :
    SimulationService {

    override suspend fun getParams(): Response<List<SimulationValues>> {
        return Response.success(
                listOf(
                    SimulationValues(
                        "homeAwayFactor",
                        5f,
                        "Fator jogar fora de casa: "
                    ),
                    SimulationValues(
                        "goalsTotalMatches",
                        3f,
                        "Fator gols marcados: "
                    ),
                    SimulationValues(
                        "goalsTotalMatches",
                        3f,
                        "Fator gols marcados: "
                    )
                )
        )
    }

    override suspend fun setSimulate(params: SimulationParams): Response<Unit> {
        delay(3000)
        return Response.success(Unit)
    }

}