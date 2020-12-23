package br.com.news.data.repository

import br.com.mindbet.common.base.Resource
import br.com.news.data.service.NewsService
import br.com.news.data.model.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException

class NewsRepositoryImpl(private val service: NewsService) : NewsRepository {

    override suspend fun getNews(): Flow<Resource<List<News>>> = flow {
        service.getNews().articles?.let {
            emit(Resource.success(it))
        } ?: emit(Resource.error(InvalidParameterException()))
    }
}