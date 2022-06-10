package com.kukeduo.kotlindemo.ui.main.project

import androidx.lifecycle.MutableLiveData
import com.kukeduo.module_common.base.base.BaseViewModel

class ProjectViewModel: BaseViewModel() {

    val title = MutableLiveData<String>()

    override fun start() {
        title.value = "Project"
    }
}