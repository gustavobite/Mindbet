package br.com.news.interactor

import br.com.mindbet.common.base.Resource
import br.com.news.NewsService
import br.com.news.model.News
import java.lang.Exception
import java.security.InvalidParameterException

class GetNewsImpl(private val service: NewsService):
    GetNews()  {
    override suspend fun execute(params: Unit): Resource<List<News>> {
        return try{
            val response = service.getNews()
            response.articles?.let {
                Resource.success(it)
            } ?: throw InvalidParameterException()
        }catch (e:Exception){
            Resource.error(e)
        }
    }
}