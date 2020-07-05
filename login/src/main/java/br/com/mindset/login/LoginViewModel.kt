package br.com.mindset.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.toSingleEvent
import br.com.mindbet.common.local.SetLocalUserData
import br.com.mindbet.common.user.User
import br.com.mindset.login.interactor.Login
import java.lang.Exception
import java.security.InvalidParameterException

class LoginViewModel(
    private val setLocalUserData: SetLocalUserData,
    private val login: Login
) : ViewModel() {

    private val _loginResponse = MutableLiveData<Resource<User>>()
    val loginResponse = _loginResponse.toSingleEvent()

    val user = MutableLiveData<User>()

    suspend fun doLogin(){
        _loginResponse.postValue(Resource.loading())
        val newValue = try{
            login(user.value!!)
        }catch (e:Exception){
            Resource.error<User>(InvalidParameterException())
        }

        _loginResponse.postValue(newValue)
    }

    suspend fun saveLogin() = user.value?.let {  setLocalUserData(it) }


}