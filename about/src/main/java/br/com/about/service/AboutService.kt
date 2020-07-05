package br.com.about.service

import br.com.about.model.AboutUsResponse
import br.com.about.model.Member
import retrofit2.Response

interface AboutService {

    //TODO: Retrofit here

    suspend fun getMembers() : Response<List<Member>>
    suspend fun getAboutUs(): Response<AboutUsResponse>
}