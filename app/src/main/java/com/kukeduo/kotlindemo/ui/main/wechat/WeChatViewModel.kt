package com.kukeduo.kotlindemo.ui.main.wechat

import androidx.lifecycle.MutableLiveData
import com.kukeduo.module_common.base.base.BaseViewModel

class WeChatViewModel: BaseViewModel() {

    val title = MutableLiveData<String>()

    override fun start() {
        title.value = "wechat"
    }
}