package br.com.mindset.login.domain

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.common.user.User
import kotlinx.coroutines.flow.Flow

abstract class Login : UseCase<User, Flow<Resource<User>>>()