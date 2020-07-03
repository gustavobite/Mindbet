package br.com.news

import br.com.news.model.News

interface NewsService {
    //TODO: Retrofit classes

    suspend fun getNews() : List<News>
}