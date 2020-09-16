package br.com.news

import br.com.news.model.News
import br.com.news.model.NewsResponse
import retrofit2.http.GET

interface NewsService {
    @GET("http://newsapi.org/v2/top-headlines?country=br&category=sports&apiKey=3363d72a9f204baa9b4c256775c88b28")
    suspend fun getNews() : NewsResponse
}