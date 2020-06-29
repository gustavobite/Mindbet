package br.com.mindbet.core.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.toSingleEvent
import br.com.mindbet.common.helper.SingleLiveEvent
import br.com.mindbet.core.news.interactor.GetNews
import br.com.mindbet.core.news.model.News

class NewsViewModel(private val getNews: GetNews) : ViewModel() {

    private val _getNewsResponse =
        MutableLiveData<Resource<List<News>>>()

    val getNewsResponse = _getNewsResponse.toSingleEvent()

    suspend fun news(){
        _getNewsResponse.postValue(Resource.loading())
        _getNewsResponse.postValue(getNews(Unit))
    }

}