package com.kukeduo.kotlindemo.bean

data class WanAndroidHomeBean(
    val curPage: Int,
    val offset: Int,
    val over: Boolean,
    val pageCount: Int,
    val size: Int,
    val total: Int,
    val datas: List<DataItems>
)

data class DataItems(
    val chapterName: String,
    val chapterId: Int,
    val link: String,
    val shareUser: String,
    val title: String,

)