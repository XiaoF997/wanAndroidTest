package com.kukeduo.kotlindemo.mvpdemo.base

import com.kukeduo.kotlindemo.mvpdemo.base.`interface`.IView
import java.lang.ref.SoftReference

abstract class BasePresenter<V: IView>(v: V) {

    open val mView: SoftReference<V> = SoftReference(v)

//    open fun onCreate(intent: Intent){
//        mView.get()?.initView()
//    }


//    fun onStart(){}
//    fun onResume(){}
//    fun onPause(){}
//    fun onStop(){}
//    fun onDestory(){}
//    fun onCreateView(arguments: Bundle?){}

}