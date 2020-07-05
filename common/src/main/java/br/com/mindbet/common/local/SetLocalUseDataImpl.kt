package br.com.mindbet.common.local

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import br.com.mindbet.common.helper.Event
import br.com.mindbet.common.user.User

class SetLocalUseDataImpl(private val userData:MutableLiveData<Event<User>>):SetLocalUserData() {
    override suspend fun execute(params: User){
        userData.postValue(Event(params))
    }
}