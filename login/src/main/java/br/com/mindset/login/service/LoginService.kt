package br.com.mindset.login.service

import br.com.mindbet.common.user.User
import retrofit2.Response

interface LoginService {

    //TODO: retrofit

    suspend fun authenticate(user: User): Response<User>

}