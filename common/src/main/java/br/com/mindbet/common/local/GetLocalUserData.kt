package br.com.mindbet.common.local

import br.com.mindbet.common.helper.Event
import br.com.mindbet.common.interactor.ObservableUseCase
import br.com.mindbet.common.user.User

abstract class GetLocalUserData : ObservableUseCase<Unit,Event<User>>()