package br.com.mindbet.common.local

import br.com.mindbet.common.helper.Event
import br.com.mindbet.common.interactor.UseCase
import br.com.mindbet.common.user.User

abstract class SetLocalUserData: UseCase<User,Unit>()