package com.kukeduo.kotlindemo.ui.login

import android.os.Bundle
import com.btpj.lib_base.utils.LogUtil
import com.kukeduo.kotlindemo.bean.User
import com.kukeduo.kotlindemo.databinding.ActivityLoginBinding
import com.kukeduo.kotlindemo.ui.main.Main2Activity
import com.kukeduo.kotlindemo.ui.register.RegisterActivity
import com.kukeduo.module_common.base.base.BaseVMBActivity
import com.kukeduo.module_common.base.utils.Preference

class LoginActivity : BaseVMBActivity<LoginViewModel, ActivityLoginBinding>() {


    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.also {
            it.loginUser.setText(mViewModel.zhanghao)
            it.loginPsd.setText(mViewModel.pwd)
        }


        mBinding.loginBtn.setOnClickListener {
            val userName = mBinding.loginUser.text.toString()
            val passWord = mBinding.loginPsd.text.toString()
            if (userName.isNotEmpty() || passWord.isNotEmpty()){
                mViewModel.login(userName, passWord){
                    Main2Activity.launch(this)
                    finish()
                }
            }
        }

        mBinding.loginToRegister.setOnClickListener {
            RegisterActivity.launch(this)
        }
    }

    override fun createObserve() {
        super.createObserve()




    }
}
