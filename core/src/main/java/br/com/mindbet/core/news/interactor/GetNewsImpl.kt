package br.com.mindbet.core.news.interactor

import br.com.mindbet.common.base.Resource
import br.com.mindbet.core.CoreService
import br.com.mindbet.core.news.model.News
import java.lang.Exception

class GetNewsImpl(private val service: CoreService):GetNews()  {
    override suspend fun execute(params: Unit): Resource<List<News>> {
        return try{
            //TODO: tratamentos para lista vazia, usuario logado etc
            Resource.success(service.getNews())
        }catch (e:Exception){
            Resource.error(e)
        }
    }
}