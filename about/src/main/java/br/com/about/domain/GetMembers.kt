package br.com.about.domain

import br.com.about.data.model.Member
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import kotlinx.coroutines.flow.Flow

abstract class GetMembers : UseCase<Unit, Flow<Resource<List<Member>>>>()