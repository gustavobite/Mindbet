package br.com.mindbet.core.simulation.create.data.model


import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class SimulationParams(
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("matches")
    var matches: List<Matches>? = listOf(),
    @SerializedName("observation")
    var observation: String? = "",
    @SerializedName("registrationDate")
    var registrationDate: String? = "",
    @SerializedName("status")
    var status: Int? = 0
)

