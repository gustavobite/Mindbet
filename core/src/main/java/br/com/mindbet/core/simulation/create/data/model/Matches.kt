package br.com.mindbet.core.simulation.create.data.model


import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import org.json.JSONObject

data class Matches(
    @SerializedName("goalsTotalMatches")
    var goalsTotalMatches: String? = "",
    @SerializedName("goalsTotalMatchesh2h")
    var goalsTotalMatchesh2h: String? = "",
    @SerializedName("homeAwayFactor")
    var homeAwayFactor: String? = "",
    @SerializedName("id")
    var id: String? = "",
    @SerializedName("name")
    var name: String? = "",
    @SerializedName("needVictory")
    var needVictory: String? = "",
    @SerializedName("simulationId")
    var simulationId: String? = "",
    @SerializedName("technique")
    var technique: String? = "",
    @SerializedName("totalMatches")
    var totalMatches: String? = "",
    @SerializedName("totalMatchesh2h")
    var totalMatchesh2h: String? = "",
    @SerializedName("victoryTotalMatches")
    var victoryTotalMatches: String? = "",
    @SerializedName("victoryTotalMatchesh2h")
    var victoryTotalMatchesh2h: String? = ""
)
fun List<SimulationValues>.toMatches(name:String):Matches{
    val json = JSONObject()
    this.map {
        json.put(it.key,it.value.toString())
    }

    json.put("name", name)

    return Gson().fromJson(json.toString(),Matches::class.java)
}