package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.CommonResponse
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines
import okhttp3.MultipartBody
import okhttp3.RequestBody

class GeneratePostViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _uploadContentResponse = MutableLiveData<CommonResponse>()
    val uploadContentResponse get() = _uploadContentResponse
    fun uploadImageContent(
        image_file: MultipartBody.Part,
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
                repository.uploadImageContent(
                    image_file,
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

    fun uploadAudioContent(
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
                repository.uploadAudioContent(
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

    fun uploadVideoContent(
        video_file: MultipartBody.Part,
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
                repository.uploadVideoContent(
                    video_file,
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