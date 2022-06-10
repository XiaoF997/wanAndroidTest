package com.kukeduo.kotlindemo.ui.main.home

import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.bean.Article
import com.kukeduo.kotlindemo.bean.BannerBean
import com.kukeduo.kotlindemo.net.DataRepository
import com.kukeduo.module_common.base.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class HomeViewModel : BaseViewModel() {

    val title = MutableLiveData<String>()

    //首页banner的集合
    val bannerListLiveData = MutableLiveData<List<BannerBean>>()

    //首页文章的集合
    val articlePageListData = MutableLiveData<PageResponse<Article>>()

    val pageSize = 10


    override fun start() {
        title.value = "home"
        getBannerData()
        getArticlePageList()
    }

    private fun getBannerData() {
        launch({
            handleRequest(DataRepository.getBanner(), {
                bannerListLiveData.value = it.data
            })
        })
    }

    fun getArticlePageList(pageNo: Int = 0) {
        articlePageListData.value
        launch({
            if (pageNo == 0) {
                // 使用async需要单独加作用域,不然没网时会崩溃
                withContext(Dispatchers.Default){
                    val pageArticle = async { DataRepository.getArticlePageList(pageNo, pageSize) }
                    val topArticle = async { DataRepository.getArticleTopList() }
                    handleRequest(pageArticle.await(),{list->
                        handleRequest(topArticle.await(),{topList->
                            (list.data.datas as ArrayList<Article>).addAll(0, topList.data)
                            articlePageListData.postValue(list.data)
                        })
                    })




                }
            }else{
                handleRequest(DataRepository.getArticlePageList(pageNo, pageSize),{
                    articlePageListData.value = it.data
                })
            }
        })
    }
}