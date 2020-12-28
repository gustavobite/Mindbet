package br.com.about.data.repository

import br.com.about.data.model.AboutUsResponse
import br.com.about.data.model.Member
import br.com.mindbet.common.base.Resource
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface AboutRepository {
    suspend fun getMembers() : Flow<Resource<List<Member>>>
    suspend fun getAboutUs(): Flow<Resource<AboutUsResponse>>
}