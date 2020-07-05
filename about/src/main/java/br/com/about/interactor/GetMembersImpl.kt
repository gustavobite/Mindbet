package br.com.about.interactor

import br.com.about.model.AboutUsResponse
import br.com.about.model.Member
import br.com.about.service.AboutService
import br.com.mindbet.common.base.Resource
import java.lang.NullPointerException

class GetMembersImpl(private val service: AboutService): GetMembers(){
    override suspend fun execute(params: Unit): Resource<List<Member>> {
        val response = service.getMembers()
        return if (response.isSuccessful){
            Resource.success(response.body())
        }else
            Resource.error(NullPointerException())
    }
}