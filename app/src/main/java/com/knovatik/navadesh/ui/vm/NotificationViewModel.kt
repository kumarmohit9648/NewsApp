package com.knovatik.navadesh.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.knovatik.navadesh.model.AuthToken
import com.knovatik.navadesh.model.CommonResponse
import com.knovatik.navadesh.model.notification.Notification
import com.knovatik.navadesh.model.notification.NotificationStatus
import com.knovatik.navadesh.network.Repository
import com.knovatik.navadesh.network.utils.Coroutines

class NotificationViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getNotificationListResponse = MutableLiveData<Notification>()
    val getNotificationListResponse get() = _getNotificationListResponse
    fun getNotificationList(request: AuthToken) {
        Coroutines.main {
            _getNotificationListResponse.postValue(repository.getNotificationList(request))
        }
    }

    private var _updateNotificationStatusResponse = MutableLiveData<CommonResponse>()
    val updateNotificationStatusResponse get() = _updateNotificationStatusResponse
    fun updateNotificationStatus(request: NotificationStatus) {
        Coroutines.main {
            _updateNotificationStatusResponse.postValue(repository.updateNotificationStatus(request))
        }
    }

}