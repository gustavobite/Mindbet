package br.com.news.data.repository

import br.com.mindbet.common.base.Resource
import br.com.news.data.model.News
import br.com.news.data.model.NewsResponse
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    suspend fun getNews() : Flow<Resource<List<News>>>
}