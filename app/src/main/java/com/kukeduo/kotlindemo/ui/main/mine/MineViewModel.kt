package com.kukeduo.kotlindemo.ui.main.mine

import androidx.lifecycle.MutableLiveData
import com.kukeduo.module_common.base.base.BaseViewModel

class MineViewModel: BaseViewModel() {

    val title = MutableLiveData<String>()

    override fun start() {
        title.value = "mine"
    }
}