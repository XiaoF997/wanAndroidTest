package com.kukeduo.kotlindemo.ui.main

import androidx.lifecycle.MutableLiveData
import com.kukeduo.kotlindemo.net.HttpManager.retrofit
import com.kukeduo.kotlindemo.net.wanAndroidApi
import com.kukeduo.module_common.base.base.BaseViewModel

class Main2ViewModel: BaseViewModel() {

    val text:MutableLiveData<String> = MutableLiveData()

    private val api by lazy {
        retrofit.create(wanAndroidApi::class.java)
    }

    fun getHomePage(page:Int ){
//        viewModelScope.launch {
//
//           val result = api.getHomeData(page)
//            println(result.toString())
//        }
    }

    override fun start() {
    }

//    fun getQueryResult(wd:String, key:String){
//
//        viewModelScope.launch {
//            val result = api.query(wd, key)
//            text.value = result.result.zi
//        }
//    }

}