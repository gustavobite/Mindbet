package br.com.about.interactor

import br.com.about.model.AboutUsResponse
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase

abstract class GetAbout: UseCase<Unit,Resource<AboutUsResponse>>()