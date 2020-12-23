package br.com.about.domain

import br.com.about.data.model.AboutUsResponse
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import kotlinx.coroutines.flow.Flow

abstract class GetAbout: UseCase<Unit, Flow<Resource<AboutUsResponse>>>()