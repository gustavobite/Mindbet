package br.com.mindset.login.data.repository

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.user.User
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface LoginRepository {
    suspend fun authenticate(user: User): Flow<Resource<User>>
}