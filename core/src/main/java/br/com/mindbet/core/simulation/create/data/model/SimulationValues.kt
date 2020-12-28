package br.com.mindbet.core.simulation.create.data.model

class SimulationValues(
    val key:String,
    var value:Float,
    val title:String,
    val initialValue:Float = 0f,
    val maxValue:Float = 10f
)