package com.newsapp.ui.vm

import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.newsapp.model.section.SectionItem
import com.newsapp.model.section.SectionItemRequest
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class JokesViewModel @ViewModelInject constructor(
    private val repository: Repository,
    @Assisted private val state: SavedStateHandle
) : ViewModel() {

    private var _getSectionItemResponse = MutableLiveData<SectionItem>()
    val getSectionItemResponse get() = _getSectionItemResponse
    fun getSectionItem(request: SectionItemRequest) {
        Coroutines.main {
            _getSectionItemResponse.postValue(repository.getSectionItem(request))
        }
    }

}