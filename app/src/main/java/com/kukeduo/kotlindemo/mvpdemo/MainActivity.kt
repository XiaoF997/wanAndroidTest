package com.kukeduo.kotlindemo.mvpdemo

import android.widget.Button
import android.widget.TextView
import com.kukeduo.kotlindemo.R
import com.kukeduo.kotlindemo.mvpdemo.base.BaseActivity

class MainActivity: BaseActivity<MainPresenter>(), MainContract.MainView {
//    private val result: TextView by lazy {
//         findViewById<TextView>(R.id.result)
//    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


//    override val mPresenter: MainPresenter = MainPresenter(this)

    override fun initView() {
//        val btn = findViewById<Button>(R.id.btn)
//        btn.setOnClickListener {
////            presenter.addUser("admin")
//            presenter.query("心","e08b373b08bf9e287f4861d2f1894664")
//        }
    }

    override fun createPresenter(): MainPresenter = MainPresenter(this)


    override fun showResult(res: String) {
//        result.text = res
    }

    override fun recycle() {
        TODO("Not yet implemented")
    }



}