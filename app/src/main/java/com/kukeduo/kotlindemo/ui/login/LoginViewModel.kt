package com.kukeduo.kotlindemo.ui.login

import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.lib_base.utils.LogUtil
import com.kukeduo.kotlindemo.bean.User
import com.kukeduo.kotlindemo.net.DataRepository
import com.kukeduo.module_common.base.base.BaseViewModel
import com.kukeduo.module_common.base.utils.Preference

class LoginViewModel: BaseViewModel() {

    var zhanghao: String by Preference("user-name", "")
    var pwd: String by Preference("pwd", "")


    override fun start() {

    }


    fun login(userName:String, psd: String, successCall: ()-> Unit){
        launch({
            handleRequest(DataRepository.login(userName, psd),{
                LogUtil.e("viewModel:     ${it.data}")
                zhanghao = it.data.username
                pwd = it.data.password

                LogUtil.e("$zhanghao .........$pwd")
                successCall()
            })
        })
    }
}