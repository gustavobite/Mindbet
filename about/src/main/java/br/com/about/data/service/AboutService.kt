package br.com.about.data.service

import br.com.about.data.model.AboutUsResponse
import br.com.about.data.model.Member
import retrofit2.Response

interface AboutService {

    //TODO: Retrofit here

    suspend fun getMembers() : Response<List<Member>>
    suspend fun getAboutUs(): Response<AboutUsResponse>
}