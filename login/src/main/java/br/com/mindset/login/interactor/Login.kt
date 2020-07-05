package br.com.mindset.login.interactor

import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.common.user.User

abstract class Login : UseCase<User,Resource<User>>()