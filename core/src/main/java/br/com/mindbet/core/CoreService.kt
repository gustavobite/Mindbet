package br.com.mindbet.core

import br.com.mindbet.core.news.model.News

interface CoreService {
    //TODO: Retrofit classes

    suspend fun getNews() : List<News>
}