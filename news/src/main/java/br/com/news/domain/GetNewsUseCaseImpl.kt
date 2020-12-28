package br.com.news.domain

import br.com.mindbet.common.base.Resource
import br.com.news.data.model.News
import br.com.news.data.repository.NewsRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class GetNewsUseCaseImpl(private val repository: NewsRepository):
    GetNewsUseCase()  {
    @ExperimentalCoroutinesApi
    override suspend fun execute(params: Unit): Flow<Resource<List<News>>> {
       return repository.getNews().catch { emit(Resource.error(it)) }
    }
}