package com.kukeduo.kotlindemo.mvpdemo

import com.kukeduo.kotlindemo.bean.IdiomsItem
import com.kukeduo.kotlindemo.net.HttpManager.retrofit
import com.kukeduo.kotlindemo.net.IdiomsApi

object MainModel: MainContract.MainModel {

    private val api: IdiomsApi = retrofit.create(IdiomsApi::class.java)

    override fun addUser(name:String, listener:(Boolean)->Unit){

        if (name == "admin"){
            listener(true)
        }else{
            listener(false)
        }
    }

    override suspend fun query(wd:String, key:String, listener:(IdiomsItem)-> Unit){
        val result = api.query(wd, key)
//        val res = result.await()
//        listener(res)
    }

}