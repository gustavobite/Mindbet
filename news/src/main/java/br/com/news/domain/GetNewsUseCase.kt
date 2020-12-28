package br.com.news.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.news.data.model.News
import kotlinx.coroutines.flow.Flow

abstract class GetNewsUseCase : UseCase<Unit, Flow<Resource<List<News>>>>()