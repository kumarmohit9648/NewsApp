package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.CommonResponse
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class GeneratePostViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _uploadContentResponse = MutableLiveData<CommonResponse>()
    val uploadContentResponse get() = _uploadContentResponse
    fun uploadContent(
        auth_token: String,
        title: String,
        content: String,
        state: String,
        district: String,
        village: String,
        address: String
    ) {
        Coroutines.main {
            _uploadContentResponse.postValue(
                repository.uploadContent(
                    auth_token,
                    title,
                    content,
                    state,
                    district,
                    village,
                    address
                )
            )
        }
    }

}