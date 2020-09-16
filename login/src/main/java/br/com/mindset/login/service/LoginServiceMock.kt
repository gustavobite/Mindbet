package br.com.mindset.login.service

import br.com.mindbet.common.extension.createUUID
import br.com.mindbet.common.user.User
import kotlinx.coroutines.delay
import retrofit2.Response

class LoginServiceMock : LoginService {
    override suspend fun authenticate(user: User): Response<User> {
        delay(3000)
        return Response.success(
            User(
                name = "Luanel",
                uid = createUUID(false),
                email = "teste@teste.com"
            )
        )
    }
}