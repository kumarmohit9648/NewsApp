package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.AuthToken
import com.newsapp.model.notification.Notification
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class NotificationViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getNotificationListResponse = MutableLiveData<Notification>()
    val getNotificationListResponse get() = _getNotificationListResponse
    fun getNotificationList(request: AuthToken) {
        Coroutines.main {
            getNotificationListResponse.postValue(repository.getNotificationList(request))
        }
    }

}