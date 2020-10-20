package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.register.RegisterRequest
import com.knovatik.navadesh.model.register.RegisterResponse
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

class RegisterViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _userRegistrationResponse = MutableLiveData<RegisterResponse>()
    val userRegistrationResponse get() = _userRegistrationResponse
    fun userRegistration(registerRequest: RegisterRequest) {
        Coroutines.main {
            _userRegistrationResponse.postValue(repository.userRegistration(registerRequest))
        }
    }

}