package com.kukeduo.kotlindemo.mvpdemo

import com.kukeduo.kotlindemo.mvpdemo.base.`interface`.IView
import com.kukeduo.kotlindemo.bean.IdiomsItem

interface MainContract {

    interface MainView: IView{
        fun showResult(res: String)

    }

    interface MainModel{
        fun addUser(name:String, listener:(Boolean)->Unit)

        suspend fun query(wd:String, key:String, listener:(IdiomsItem)-> Unit)
    }

}