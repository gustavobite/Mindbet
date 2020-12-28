package br.com.mindset.login.data.repository

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.user.User
import br.com.mindset.login.data.service.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.Response

class LoginRepositoryImpl(private val service: LoginService) :
    LoginRepository {
    override suspend fun authenticate(user: User): Flow<Resource<User>> = flow {
        val response = service.authenticate(user)
        if(response.isSuccessful) emit(Resource.success(response.body()))
        else emit(Resource.error(Exception(response.errorBody().toString())))
    }
}