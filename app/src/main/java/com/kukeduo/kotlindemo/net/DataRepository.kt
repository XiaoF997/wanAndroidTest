package com.kukeduo.kotlindemo.net

import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.http.BaseRepository
import com.btpj.wanandroid.data.bean.Article
import com.kukeduo.kotlindemo.bean.User
import com.kukeduo.kotlindemo.bean.BannerBean
import com.kukeduo.kotlindemo.bean.ProjectTitle
import com.kukeduo.module_common.base.bean.ApiResponse

object DataRepository: BaseRepository(), wanAndroidApi {

    private val api by lazy {
        HttpManager.retrofit.create(wanAndroidApi::class.java)
    }

    override suspend fun login(username: String, pwd: String): ApiResponse<User> {
        return apiCall { api.login(username, pwd) }
    }

    override suspend fun getBanner(): ApiResponse<List<BannerBean>> {
        return apiCall { api.getBanner() }
    }

    override suspend fun getArticleTopList(): ApiResponse<List<Article>> {
        return apiCall {
            api.getArticleTopList()
        }
    }

    override suspend fun getArticlePageList(
        pageNo: Int,
        pageSize: Int
    ): ApiResponse<PageResponse<Article>> {
        return apiCall { api.getArticlePageList(pageNo, pageSize) }
    }

    override suspend fun register(
        username: String,
        pwd: String,
        pwdSure: String
    ): ApiResponse<Any?> {
        return apiCall { api.register(username, pwd, pwdSure) }
    }

    override suspend fun getProjectTitleList(): ApiResponse<List<ProjectTitle>> {
        return apiCall { api.getProjectTitleList() }
    }
}