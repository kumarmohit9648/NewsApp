package com.newsapp.network

import com.newsapp.model.menu.MenuCategories
import com.newsapp.network.interfaces.Api
import com.newsapp.network.retrofit.SafeApiRequest

class Repository(private val api: Api) : SafeApiRequest() {

    suspend fun getMenuCategory(): MenuCategories {
        var response = MenuCategories(null, "Network Error", false, "")
        try {
            response = apiRequest { api.getMenuCategory() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

}