package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.profile.ProfileDetail
import com.knovatik.navadesh.model.profile.UpdateProfile
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

class UpdateProfileViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _updateProfileResponse = MutableLiveData<ProfileDetail>()
    val updateProfileResponse get() = _updateProfileResponse
    fun updateProfile(request: UpdateProfile) {
        Coroutines.main {
            _updateProfileResponse.postValue(repository.updateProfile(request))
        }
    }

}