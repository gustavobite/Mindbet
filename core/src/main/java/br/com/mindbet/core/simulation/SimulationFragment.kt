package br.com.mindbet.core.simulation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.mindbet.common.base.BaseFragment
import br.com.mindbet.core.R

class SimulationFragment:BaseFragment() {
    override fun getScreenName(): String = "Simulate screen"
    override fun layoutResource(): Int = R.layout.fragment_simulate


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource(),container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }
}