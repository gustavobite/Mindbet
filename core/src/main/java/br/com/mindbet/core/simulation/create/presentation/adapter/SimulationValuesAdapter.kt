package br.com.mindbet.core.simulation.create.presentation.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.mindbet.core.R
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import com.google.android.material.slider.Slider
import kotlin.math.roundToInt


class SimulationValuesAdapter() :
    RecyclerView.Adapter<SimulationValuesAdapter.NewsHolder>() {


    var items: List<SimulationValues> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.adapter_item_simulation, parent, false)
        return NewsHolder(
            view
        )
    }

    override fun getItemCount() = items.count()

    override fun onBindViewHolder(holder: NewsHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
    }


    class NewsHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        private val title = itemView.findViewById<TextView>(R.id.title)
        private val itemValue = itemView.findViewById<TextView>(R.id.itemValue)
        private val slider = itemView.findViewById<Slider>(R.id.slider)


        fun bind(item: SimulationValues) {
            title.text = item.title
            itemValue.text = item.value.roundToInt().toString()
            slider.apply {
                value = item.value
                valueFrom = item.initialValue
                valueTo = item.maxValue
                addOnChangeListener { _, value, _ ->
                    itemValue.text = value.roundToInt().toString()
                    item.value = value
                }

            }
        }

    }
}