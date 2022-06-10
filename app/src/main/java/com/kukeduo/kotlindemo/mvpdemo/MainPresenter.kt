package com.kukeduo.kotlindemo.mvpdemo

import com.kukeduo.kotlindemo.mvpdemo.base.BasePresenter
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class MainPresenter(view: MainContract.MainView) : BasePresenter<MainContract.MainView>(view) {

    val scope = MainScope()

    fun addUser(name: String) {
        MainModel.addUser(name) {
            if (it) {
                mView.get()?.showToast("成功")
            } else {
                mView.get()?.showToast("失败")

            }
        }
    }

    fun query(wd: String, key: String) {
//        scope.launch {
//            MainModel.query(wd, key) {
//                if (it.result.zi != "") {
//                    mView.get()?.showResult(it.result.zi)
//                } else {
//                    mView.get()?.showToast("${it.error_code}")
//                }
//            }
//        }
    }
}