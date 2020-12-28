package br.com.mindbet.core.simulation.create.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mindbet.common.base.BaseFragment
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.show
import br.com.mindbet.common.extension.showOrHide
import br.com.mindbet.common.extension.text
import br.com.mindbet.core.R
import br.com.mindbet.core.simulation.create.data.model.SimulationParams
import br.com.mindbet.core.simulation.create.data.model.SimulationValues
import br.com.mindbet.core.simulation.create.data.model.toMatches
import br.com.mindbet.core.simulation.create.presentation.adapter.SimulationValuesAdapter
import kotlinx.android.synthetic.main.fragment_simulate.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class SimulationFragment : BaseFragment() {

    private val viewModel: SimulationViewModel by viewModel()
    private val adapterList1 =
        SimulationValuesAdapter()
    private val adapterList2 =
        SimulationValuesAdapter()

    override fun getScreenName(): String = "Simulate screen"
    override fun layoutResource(): Int = R.layout.fragment_simulate


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutResource(), container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        initList()
        setupObservers()
        setupListeners()
    }

    private fun initList() {
        team1List.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SimulationFragment.adapterList1
        }

        team2List.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@SimulationFragment.adapterList2
        }

        launch { viewModel.getValues() }
    }

    private fun setupObservers() {
        viewModel.simulationValuesResponse.observe(viewLifecycleOwner, Observer { handleValuesResponse(it) })
    }

    private fun handleValuesResponse(response: Resource<List<SimulationValues>>) {
        when (response.status) {
            Resource.Status.SUCCESS,
            Resource.Status.CACHE -> setupView(response.data)
            Resource.Status.ERROR -> TODO()
            else -> {
            }
        }
    }

    private fun setupView(list: List<SimulationValues>?) {
        list.let {
            adapterList1.items = it ?: arrayListOf()
            adapterList2.items = it ?: arrayListOf()
        }
    }

    private fun setupListeners() {
        team1Values.setOnClickListener {
            team1List.showOrHide {
                if (it == View.VISIBLE) imageView.setImageResource(R.drawable.ic_arrow_up)
                else imageView.setImageResource(R.drawable.ic_arrow_down)
            }
        }
        team2Values.setOnClickListener {
            team2List.showOrHide {
                if (it == View.VISIBLE) imageView2.setImageResource(R.drawable.ic_arrow_up)
                else imageView2.setImageResource(R.drawable.ic_arrow_down)
            }
        }


        btnSimulate.setOnClickListener {
            if (team1.text().isNotEmpty() || team2.text().isNotEmpty()) {
                launch {  viewModel.setSimulation(
                    adapterList1.items.toMatches(team1.text()),
                    adapterList2.items.toMatches(team2.text()))
                }
            }
        }
    }
}