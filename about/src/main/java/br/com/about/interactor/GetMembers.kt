package br.com.about.interactor

import br.com.about.model.Member
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase

abstract class GetMembers : UseCase<Unit,Resource<List<Member>>>()