package br.com.about

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import br.com.about.domain.GetAbout
import br.com.about.domain.GetMembers
import br.com.about.model.AboutUsResponse
import br.com.about.model.Member
import br.com.mindbet.common.base.Resource
import br.com.mindbet.common.extension.combineWith
import br.com.mindbet.common.extension.toSingleEvent
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map

@FlowPreview
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
    }

    private suspend fun getAboutUs(){
        _getAboutResponse.postValue(Resource.loading())
        getAbout(Unit).flatMapMerge {
            _getAboutResponse.postValue(it)
            _getMembersResponse.postValue(Resource.loading())
            getMembers(Unit)
        }.collect {
            _getMembersResponse.postValue(it)
        }
    }
}