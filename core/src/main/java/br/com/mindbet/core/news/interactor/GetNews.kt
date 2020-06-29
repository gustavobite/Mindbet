package br.com.mindbet.core.news.interactor

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.core.news.model.News

abstract class GetNews : UseCase<Unit,Resource<List<News>>>()