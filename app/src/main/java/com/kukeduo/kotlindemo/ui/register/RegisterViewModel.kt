package com.kukeduo.kotlindemo.ui.register

import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.kukeduo.kotlindemo.net.DataRepository
import com.kukeduo.module_common.base.base.BaseViewModel

class RegisterViewModel: BaseViewModel() {


    override fun start() {

    }


    fun register(username: String,
                 pwd: String,
                 pwdSure: String, successCall: () -> Any? ){
        launch({
            handleRequest(DataRepository.register(username, pwd, pwdSure), {
                LogUtil.e(it.toString())
                successCall()
            })
        })
    }
}