package br.com.mindbet.common.local

import androidx.lifecycle.LiveData
import br.com.mindbet.common.helper.Event
import br.com.mindbet.common.user.User

class GetLocalUseDataImpl(private val userData:LiveData<Event<User>>):GetLocalUserData() {
    override fun execute(params: Unit): LiveData<Event<User>> {
        return userData
    }
}