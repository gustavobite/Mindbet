package br.com.about.interactor

import br.com.about.model.AboutUsResponse
import br.com.about.service.AboutService
import br.com.mindbet.common.base.Resource
import java.lang.NullPointerException

class GetAboutImpl(private val service: AboutService): GetAbout(){
    override suspend fun execute(params: Unit): Resource<AboutUsResponse> {
        val response = service.getAboutUs()
        return if (response.isSuccessful){
            Resource.success(response.body())
        }else
            Resource.error(NullPointerException())
    }
}