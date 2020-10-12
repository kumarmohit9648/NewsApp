package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.register.RegisterRequest
import com.newsapp.model.register.RegisterResponse
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class RegisterViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _userRegistrationResponse = MutableLiveData<RegisterResponse>()
    val userRegistrationResponse get() = _userRegistrationResponse
    fun userRegistration(registerRequest: RegisterRequest) {
        Coroutines.main {
            userRegistrationResponse.postValue(repository.userRegistration(registerRequest))
        }
    }

}