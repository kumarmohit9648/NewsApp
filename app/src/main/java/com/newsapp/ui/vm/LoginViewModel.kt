package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.login.LoginRequest
import com.newsapp.model.register.RegisterResponse
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

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