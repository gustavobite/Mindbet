package br.com.mindset.login.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.toSingleEvent
import br.com.mindbet.common.local.SetLocalUserData
import br.com.mindbet.common.user.User
import br.com.mindset.login.domain.Login
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.collect
import java.lang.Exception
import java.security.InvalidParameterException

@InternalCoroutinesApi
class LoginViewModel(
    private val setLocalUserData: SetLocalUserData,
    private val login: Login
) : ViewModel() {

    private val _loginResponse = MutableLiveData<Resource<User>>()
    val loginResponse = _loginResponse.toSingleEvent()

    val user = MutableLiveData<User>()

    suspend fun doLogin(){

        _loginResponse.postValue(Resource.loading())
         user.value?.let {
            login(it).collect{ logging ->
                _loginResponse.postValue(logging)
            }
        } ?: kotlin.run {
             _loginResponse.postValue(Resource.error(InvalidParameterException())) }
    }

    suspend fun saveLogin() = user.value?.let {  setLocalUserData(it) }


}