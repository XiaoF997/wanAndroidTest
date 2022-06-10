package com.kukeduo.kotlindemo.ui.login

import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.kukeduo.kotlindemo.net.DataRepository
import com.kukeduo.module_common.base.base.BaseViewModel

class LoginViewModel: BaseViewModel() {



    override fun start() {

    }


    fun login(userName:String, psd: String, successCall: ()-> Unit){
        launch({
            handleRequest(DataRepository.login(userName, psd),{
                LogUtil.e(it.data.toString())
                successCall
            })
        })
    }
}