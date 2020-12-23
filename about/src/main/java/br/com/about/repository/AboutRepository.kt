package br.com.about.repository

import br.com.about.model.AboutUsResponse
import br.com.about.model.Member
import br.com.mindbet.common.base.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AboutRepository {
    suspend fun getMembers() : Flow<Resource<List<Member>>>
    suspend fun getAboutUs(): Flow<Resource<AboutUsResponse>>
}