package com.kukeduo.kotlindemo.ui.main.project

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.handleRequest
import com.btpj.lib_base.ext.launch
import com.btpj.wanandroid.data.bean.Article
import com.kukeduo.kotlindemo.bean.ProjectTitle
import com.kukeduo.kotlindemo.net.DataRepository
import com.kukeduo.module_common.base.base.BaseViewModel

class ProjectViewModel: BaseViewModel() {

    private val title = MutableLiveData<String>()
    val _title: LiveData<String> = title

    private val classListData = MutableLiveData<List<ProjectTitle>>()
     val _classListData:LiveData<List<ProjectTitle>> = classListData

    private val projectPageList = MutableLiveData<PageResponse<Article>>()
    val _projectPageList:LiveData<PageResponse<Article>> = projectPageList

    val pageSize = 10

    override fun start() {
        title.value = "Project"
        getClassListData()
    }


    fun getClassListData(){
        launch({
            handleRequest(DataRepository.getProjectTitleList(),{
                classListData.value = it.data
            })
        })
    }

    fun getProjectPageList(cid:Int, pageNo: Int = 0, pageSize:Int = this.pageSize){
        launch({
            handleRequest(DataRepository.getProjectPageList(pageNo, pageSize, cid),{
                projectPageList.value = it.data
            })
        })
    }
}