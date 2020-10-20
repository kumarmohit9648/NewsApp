package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.constants.AppConstant
import com.knovatik.navadesh.model.AuthToken
import com.knovatik.navadesh.model.CommonResponse
import com.knovatik.navadesh.model.profile.ProfileDetail
import com.knovatik.navadesh.model.profile.UpdateProfileImage
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines
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