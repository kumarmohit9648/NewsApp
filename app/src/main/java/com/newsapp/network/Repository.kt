package com.newsapp.network

import com.newsapp.model.menu.MenuCategories
import com.newsapp.model.posts.Posts
import com.newsapp.model.posts.PostsRequest
import com.newsapp.model.submenu.SubMenuCategories
import com.newsapp.model.submenu.SubMenuRequest
import com.newsapp.network.interfaces.Api
import com.newsapp.network.retrofit.SafeApiRequest
import javax.inject.Inject

class Repository @Inject constructor(private val _api: Api) : SafeApiRequest() {

    suspend fun getMenuCategory(): MenuCategories {
        var response = MenuCategories(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getMenuCategory() }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getSubcategoryList(subMenuRequest: SubMenuRequest): SubMenuCategories {
        var response = SubMenuCategories(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getSubcategoryList(subMenuRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

    suspend fun getPosts(postsRequest: PostsRequest): Posts {
        var response = Posts(null, "Network Error", false, "")
        try {
            response = apiRequest { _api.getPosts(postsRequest) }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }

}