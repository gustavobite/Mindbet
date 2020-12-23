package br.com.about.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.about.R
import br.com.about.presentation.adapter.MembersAdapter
import br.com.about.databinding.FragmentAboutBinding
import br.com.mindbet.common.base.BaseFragment
import br.com.mindbet.common.base.Resource
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class AboutFragment : BaseFragment() {

    private var binding: FragmentAboutBinding? = null
    private val adapter = MembersAdapter()
    private val viewModel : AboutViewModel by inject()


    override fun getScreenName(): String = "Sobre"
    override fun layoutResource(): Int = R.layout.fragment_about

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,layoutResource(),container,false)


        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObservers()

        binding?.list?.apply {
            layoutManager = LinearLayoutManager(context).also { it.orientation = RecyclerView.HORIZONTAL }
            adapter = this@AboutFragment.adapter
        }

        launch {
            viewModel.callServices()
        }

    }

    private fun setupObservers(){
        viewModel.combinedLiveData.observe(viewLifecycleOwner, Observer { finished ->
            if (finished){
                // TODO: set Loading to false
            }
        })

        viewModel.getAboutRespose.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS, Resource.Status.CACHE ->{
                    binding?.aboutus = it.data
                }
                Resource.Status.ERROR -> TODO()
                Resource.Status.LOADING -> {}
            }
        })

        viewModel.getMembersRespose.observe(viewLifecycleOwner, Observer {
            when(it.status){
                Resource.Status.SUCCESS, Resource.Status.CACHE ->{
                    adapter.items = it.data ?: emptyList()
                }
                Resource.Status.ERROR -> TODO()
                Resource.Status.LOADING -> {}
            }
        })
    }
}
