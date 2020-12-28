package br.com.about.domain

import br.com.about.data.model.Member
import br.com.about.data.repository.AboutRepository
import br.com.about.data.service.AboutService
import br.com.mindbet.common.base.Resource
import kotlinx.coroutines.flow.Flow
import java.lang.NullPointerException

class GetMembersImpl(private val repository: AboutRepository): GetMembers(){
    override suspend fun execute(params: Unit): Flow<Resource<List<Member>>> = repository.getMembers()
}