package com.kukeduo.module_common.base.bean

data class ApiResponse<T>(
    val errorCode: Int,
    val errorMsg: String,
    val data: T,
)