package com.kukeduo.kotlindemo.mvpdemo.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.kukeduo.kotlindemo.mvpdemo.base.`interface`.IView

abstract class BaseActivity<P : BasePresenter<out IView>> : AppCompatActivity(), IView {

//    private val mAllPresenter = HashSet<BasePresenter<*>>()

    lateinit var presenter: P

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
//        addPresenters()
        presenter = createPresenter()
//        mAllPresenter.forEach{
//            it.onCreate(intent)
//        }
        initView()
    }


    //    override fun onStart() {
//        super.onStart()
//        mAllPresenter.forEach {
//            it.onStart()
//        }
//    }
//
//    override fun onResume() {
//        super.onResume()
//        mAllPresenter.forEach {
//            it.onResume()
//        }
//    }
//
//    override fun onPause() {
//        super.onPause()
//        mAllPresenter.forEach {
//            it.onPause()
//        }
//    }
//
//    override fun onStop() {
//        super.onStop()
//        mAllPresenter.forEach{
//            it.onStop()
//        }
//    }
//
    override fun onDestroy() {
        super.onDestroy()
//        mAllPresenter.forEach{
//            it.onDestory()
//        }
        recycle()
    }

//    open fun getPresenters(): MutableList<BasePresenter<*>> {
//        return mutableListOf(mPresenter)
//    }
//
//    open fun addPresenters() {
//        getPresenters().forEach {
//            mAllPresenter.add(it)
//        }
//    }

    abstract fun getLayoutId(): Int

    abstract fun createPresenter(): P

    abstract fun recycle()

    override fun showDialog() {
        TODO("Not yet implemented")
    }

    override fun dismissDialog() {
        TODO("Not yet implemented")
    }

    override fun showToast(value: String) {
        Toast.makeText(this, value, Toast.LENGTH_SHORT).show()
    }
}