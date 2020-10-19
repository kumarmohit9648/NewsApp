package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.CommonResponse
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines
import okhttp3.MultipartBody
import okhttp3.RequestBody

class GeneratePostViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _uploadContentResponse = MutableLiveData<CommonResponse>()
    val uploadContentResponse get() = _uploadContentResponse
    fun uploadContent(
        image_file: MultipartBody.Part,
        video_file: MultipartBody.Part,
        audio_file: MultipartBody.Part,
        auth_token: RequestBody,
        title: RequestBody,
        content: RequestBody,
        state: RequestBody,
        district: RequestBody,
        village: RequestBody,
        address: RequestBody
    ) {
        Coroutines.main {
            _uploadContentResponse.postValue(
                repository.uploadContent(
                    image_file,
                    video_file,
                    audio_file,
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