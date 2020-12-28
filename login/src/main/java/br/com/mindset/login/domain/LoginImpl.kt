package br.com.mindset.login.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.base.toResourceFlow
import br.com.mindbet.common.extension.isNotNullOrEmpty
import br.com.mindbet.common.user.User
import br.com.mindset.login.data.repository.LoginRepository
import br.com.mindset.login.data.service.LoginService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.security.InvalidParameterException

class LoginImpl(private val service: LoginRepository) : Login() {
    override suspend fun execute(params: User): Flow<Resource<User>> {
        return try{
            if(params.email.isNotNullOrEmpty() && params.password.isNotNullOrEmpty()){
                service.authenticate(params)
            } else throw InvalidParameterException()
        }catch (exception:Exception){
            exception.toResourceFlow()
        }
    }
}