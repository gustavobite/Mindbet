package br.com.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.about.interactor.GetAbout
import br.com.about.interactor.GetMembers
import br.com.about.model.AboutUsResponse
import br.com.about.model.Member
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.combineWith
import br.com.mindbet.common.extension.toSingleEvent

class AboutViewModel(
    private val getAbout: GetAbout,
    private val getMembers: GetMembers
)
    : ViewModel() {

    private val _getAboutResponse = MutableLiveData<Resource<AboutUsResponse>>()
    val getAboutRespose = _getAboutResponse.toSingleEvent()

    private val _getMembersResponse = MutableLiveData<Resource<List<Member>>>()
    val getMembersRespose = _getMembersResponse.toSingleEvent()

    val combinedLiveData = getAboutRespose.combineWith(getMembersRespose){ about, members ->
        about?.status != Resource.Status.LOADING && members?.status != Resource.Status.LOADING
    }

    suspend fun callServices(){
        getAboutUs()
        getMembers()
    }

    private suspend fun getAboutUs(){
        _getAboutResponse.postValue(Resource.loading())
        _getAboutResponse.postValue(getAbout(Unit))
    }

    private suspend fun getMembers(){
        _getMembersResponse.postValue(Resource.loading())
        _getMembersResponse.postValue(getMembers(Unit))
    }

}