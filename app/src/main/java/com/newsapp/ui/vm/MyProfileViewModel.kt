package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.constants.AppConstant
import com.newsapp.model.AuthToken
import com.newsapp.model.CommonResponse
import com.newsapp.model.profile.ProfileDetail
import com.newsapp.model.profile.UpdateProfileImage
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines
import com.pixplicity.easyprefs.library.Prefs

class MyProfileViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getProfileResponse = MutableLiveData<ProfileDetail>()
    val getProfileResponse get() = _getProfileResponse
    fun getProfile() {
        Coroutines.main {
            _getProfileResponse.postValue(
                repository.getProfile(
                    AuthToken(
                        Prefs.getString(
                            AppConstant.AUTH_TOKEN,
                            ""
                        )
                    )
                )
            )
        }
    }

    private var _imageProfileResponse = MutableLiveData<CommonResponse>()
    val imageProfileResponse get() = _imageProfileResponse
    fun imageProfile(request: UpdateProfileImage) {
        Coroutines.main {
            _imageProfileResponse.postValue(repository.updateProfileImage(request))
        }
    }

}