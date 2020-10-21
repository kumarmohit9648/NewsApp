package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.social.SocialLoginRequest
import com.knovatik.navadesh.model.social.SocialProfileDetail
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

class LoginOptionViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _socialRegistrationResponse = MutableLiveData<SocialProfileDetail>()
    val socialRegistrationResponse get() = _socialRegistrationResponse
    fun socialRegistration(request: SocialLoginRequest) {
        Coroutines.main {
            _socialRegistrationResponse.postValue(repository.socialRegistration(request))
        }
    }

}