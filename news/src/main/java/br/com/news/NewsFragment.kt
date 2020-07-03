package br.com.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.mindbet.common.base.BaseFragment
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.hide
import br.com.mindbet.common.extension.show
import br.com.mindbet.common.extension.then
import br.com.news.adapter.NewsAdapter
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

class NewsFragment: BaseFragment() {

    override fun getScreenName(): String = "news fragment"
    override fun layoutResource(): Int = R.layout.fragment_news

    private val adapter = NewsAdapter(this::onItemClick)
    private val viewModel : NewsViewModel by viewModel()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource(),container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupObserver()

        launch { viewModel.news() }

        list.apply{
            layoutManager = LinearLayoutManager(context)
            adapter = this@NewsFragment.adapter
        }
    }

    private fun setupObserver(){
        viewModel.getNewsResponse.observe(viewLifecycleOwner, Observer { resource ->
            manageLoading(resource.status == Resource.Status.LOADING)
            when(resource.status){
                Resource.Status.SUCCESS, Resource.Status.CACHE -> {
                    resource.data?.let {  adapter.items  = it }
                }
                Resource.Status.ERROR -> {}
                else -> {}
            }
        })
    }

    private fun manageLoading(isLoading:Boolean){
        isLoading then progress.showProgress() ?: progress.hideProgress()
    }

    private fun showError(){

    }

    private fun onItemClick(news: br.com.news.model.News){
        showToast(news.title!!)
    }
}