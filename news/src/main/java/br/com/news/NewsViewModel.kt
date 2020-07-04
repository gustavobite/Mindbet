package br.com.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.toSingleEvent
import br.com.mindbet.common.helper.SingleLiveEvent
import br.com.mindbet.navigation.NavigationViewModel
import br.com.news.interactor.GetNews
import br.com.news.model.News
import com.google.android.material.navigation.NavigationView

class NewsViewModel(private val getNews: GetNews) : NavigationViewModel() {

    private val _getNewsResponse =
        MutableLiveData<Resource<List<News>>>()
    val getNewsResponse = _getNewsResponse.toSingleEvent()

    private val _newsSelected =
        MutableLiveData<News>()
    val newsSelected : LiveData<News> = _newsSelected

    suspend fun news(){
        _getNewsResponse.postValue(Resource.loading())
        _getNewsResponse.postValue(getNews(Unit))
    }


    fun setNewsSelected(news: News){
        _newsSelected.postValue(news)
    }

}