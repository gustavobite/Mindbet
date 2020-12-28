package br.com.about.data.repository

import br.com.about.data.model.AboutUsResponse
import br.com.about.data.model.Member
import br.com.about.data.service.AboutService
import br.com.mindbet.common.base.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import java.security.InvalidParameterException

class AboutRepositoryImpl(private val service: AboutService) :
    AboutRepository {
    override suspend fun getMembers(): Flow<Resource<List<Member>>> = flow {
        val newValue = service.getMembers().body()?.let {
            Resource.success(it)
        } ?: Resource.error(InvalidParameterException())

        emit(newValue)
    }

    override suspend fun getAboutUs(): Flow<Resource<AboutUsResponse>> = flow {
        val newValue = service.getAboutUs().body()?.let {
            Resource.success(it)
        } ?: Resource.error(InvalidParameterException())

        emit(newValue)
    }
}