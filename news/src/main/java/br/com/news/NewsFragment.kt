package br.com.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import br.com.mindbet.common.base.Resource
import br.com.mindbet.navigation.NavigationFragment
import br.com.mindbet.navigation.NavigationViewModel
import br.com.news.adapter.NewsAdapter
import br.com.news.model.News
import com.google.android.material.imageview.ShapeableImageView
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class NewsFragment: NavigationFragment(), SwipeRefreshLayout.OnRefreshListener {

    override fun getScreenName(): String = "news fragment"
    override fun layoutResource(): Int = R.layout.fragment_news

    private val adapter = NewsAdapter(this::onItemClick)
    private val viewModel : NewsViewModel by sharedViewModel()
    private var imageView: ShapeableImageView? = null
    private var titleView:TextView? = null



    override fun canBack(): Boolean = true
    override fun getViewModel(): NavigationViewModel = viewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(layoutResource(),container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        postponeEnterTransition()
        list.viewTreeObserver
            .addOnPreDrawListener {
                startPostponedEnterTransition()
                true
            }

        setupObserver()
        swipeLayout.setOnRefreshListener(this)

        if(adapter.items.isEmpty()) {
            swipeLayout.post {
                swipeLayout.isRefreshing = true
                showNews()
            }
        }

        list.apply{
            onListCheck()
            layoutManager = LinearLayoutManager(context)
            adapter = this@NewsFragment.adapter
        }

    }

    override fun onRefresh() {
        showNews()
    }

    private fun showNews() =  launch { viewModel.news() }

    private fun setupObserver(){
        viewModel.getNewsResponse.observe(viewLifecycleOwner, Observer { resource ->
            when(resource.status){
                Resource.Status.SUCCESS, Resource.Status.CACHE -> {
                    resource.data?.let {  adapter.items  = it }
                    swipeLayout.isRefreshing = false
                }
                Resource.Status.ERROR -> {
                    swipeLayout.isRefreshing = false
                }
                else -> {}
            }
        })
    }


    private fun showError(){

    }

    private fun onListCheck(){
        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                super.onChanged()
                checkEmpty()
            }

            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)
                checkEmpty()
            }

            override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                super.onItemRangeRemoved(positionStart, itemCount)
                checkEmpty()
            }

            fun checkEmpty() {
                //TODO: Empty view
//                empty_view.visibility = (if (adapter.itemCount == 0) View.VISIBLE else View.GONE)
            }
        })
    }

    private fun onItemClick(news: News, imageView:ShapeableImageView, titleView:TextView){
        this.imageView = imageView
        this.titleView = titleView
        viewModel.setNewsSelected(news)
        findNavController().navigate(R.id.newsDetails,null,null,getExtras())
    }

    override fun getExtras(): FragmentNavigator.Extras {
        this.imageView?.let {
            return FragmentNavigatorExtras(
                it to it.transitionName,
                this.titleView!! to this.titleView!!.transitionName
            )
        }
        return FragmentNavigatorExtras()
    }
}