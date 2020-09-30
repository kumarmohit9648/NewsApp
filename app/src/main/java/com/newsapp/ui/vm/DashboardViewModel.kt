package com.newsapp.ui.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.newsapp.model.menu.MenuCategories
import com.newsapp.network.Repository
import com.newsapp.network.utils.Coroutines

class DashboardViewModel(private val repository: Repository) : ViewModel() {

    private var getMenuCategoryResponse = MutableLiveData<MenuCategories>()
    public fun getMenuCategoryResponse() = getMenuCategoryResponse
    fun getMenuCategory() {
        Coroutines.main {
            getMenuCategoryResponse.postValue(repository.getMenuCategory())
        }
    }

}