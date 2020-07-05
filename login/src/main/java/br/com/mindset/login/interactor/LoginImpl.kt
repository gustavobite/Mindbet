package br.com.mindset.login.interactor

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.isNotNullOrEmpty
import br.com.mindbet.common.user.User
import br.com.mindset.login.service.LoginService
import java.security.InvalidParameterException

class LoginImpl(private val service:LoginService) : Login() {
    override suspend fun execute(params: User): Resource<User> {
        return try{
            if(params.email.isNotNullOrEmpty() && params.password.isNotNullOrEmpty()){
                val response = service.authenticate(params)
                if(response.isSuccessful) Resource.success(response.body())
                else throw InvalidParameterException()
            } else throw InvalidParameterException()
        }catch (e:Exception){
            Resource.error(e)
        }
    }
}