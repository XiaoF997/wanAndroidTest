package com.kukeduo.kotlindemo.ui.main.square

import androidx.lifecycle.MutableLiveData
import com.kukeduo.module_common.base.base.BaseViewModel

class SquareViewModel: BaseViewModel() {

    val title = MutableLiveData<String>()

    override fun start() {
        title.value = "square"
    }
}