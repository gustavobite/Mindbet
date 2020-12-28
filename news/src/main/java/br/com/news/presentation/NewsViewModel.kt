package br.com.news.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.toSingleEvent
import br.com.mindbet.navigation.NavigationViewModel
import br.com.news.domain.GetNewsUseCase
import br.com.news.data.model.News
import kotlinx.coroutines.flow.collect

class NewsViewModel(private val getNewsUseCase: GetNewsUseCase) : NavigationViewModel() {

    private val _getNewsResponse =
        MutableLiveData<Resource<List<News>>>()
    val getNewsResponse = _getNewsResponse.toSingleEvent()

    private val _newsSelected =
        MutableLiveData<News>()
    val newsSelected : LiveData<News> = _newsSelected

    suspend fun news(){
        _getNewsResponse.postValue(Resource.loading())
        getNewsUseCase(Unit).collect {
            _getNewsResponse.postValue(it)
        }
    }


    fun setNewsSelected(news: News){
        _newsSelected.postValue(news)
    }

}