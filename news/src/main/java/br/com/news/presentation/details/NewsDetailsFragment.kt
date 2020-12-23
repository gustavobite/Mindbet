package br.com.news.presentation.details

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.transition.TransitionInflater
import br.com.mindbet.common.base.BaseActivity
import br.com.mindbet.common.base.BaseFragment
import br.com.news.presentation.NewsViewModel
import br.com.news.R
import br.com.news.databinding.FragmentNewsDetailsBinding
import com.google.android.material.appbar.AppBarLayout
import kotlinx.android.synthetic.main.fragment_news_details.*
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class NewsDetailsFragment : BaseFragment() {

    private val viewModel : NewsViewModel by sharedViewModel()
    private var binding : FragmentNewsDetailsBinding? = null

    override fun getScreenName(): String = "Detalhe das noticias"

    override fun layoutResource(): Int = R.layout.fragment_news_details

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater,layoutResource(),container,false)

        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setupToolbar(toolbar,navigationIcon = BaseActivity.NavigationIcon.ArrowIcon, isDarkMenu = true)

        viewModel.newsSelected.observe(viewLifecycleOwner, Observer {
            binding?.item = it
            binding?.executePendingBindings()
        })


        var isShow = true
        var scrollRange = -1
        app_bar.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { barLayout, verticalOffset ->
            if (scrollRange == -1){
                scrollRange = barLayout?.totalScrollRange!!
            }
            if (scrollRange + verticalOffset == 0){
                collapsinToolbar.title = viewModel.newsSelected.value?.author
                isShow = true
            } else if (isShow){
                collapsinToolbar.title = " " //careful there should a space between double quote otherwise it wont work
                isShow = false
            }
        })

        btnMore.setOnClickListener {
            val browserIntent =
                Intent(Intent.ACTION_VIEW, Uri.parse(viewModel.newsSelected.value?.url))
            startActivity(browserIntent)
        }
    }
}