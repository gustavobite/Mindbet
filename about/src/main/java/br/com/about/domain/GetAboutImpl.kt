package br.com.about.domain

import br.com.about.model.AboutUsResponse
import br.com.about.repository.AboutRepository
import br.com.about.service.AboutService
import br.com.mindbet.common.base.Resource
import kotlinx.coroutines.flow.Flow
import java.lang.NullPointerException

class GetAboutImpl(private val repository: AboutRepository): GetAbout(){
    override suspend fun execute(params: Unit): Flow<Resource<AboutUsResponse>> = repository.getAboutUs()
}