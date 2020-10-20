package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.login.LoginRequest
import com.knovatik.navadesh.model.register.RegisterResponse
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

class LoginViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _loginResponse = MutableLiveData<RegisterResponse>()
    val loginResponse get() = _loginResponse
    fun login(request: LoginRequest) {
        Coroutines.main {
            _loginResponse.postValue(repository.login(request))
        }
    }

}