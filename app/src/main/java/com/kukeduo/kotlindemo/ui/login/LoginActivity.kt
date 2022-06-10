package com.kukeduo.kotlindemo.ui.login

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.kukeduo.kotlindemo.databinding.ActivityLoginBinding
import com.kukeduo.kotlindemo.ui.register.RegisterActivity
import com.kukeduo.module_common.base.base.BaseVMBActivity

class LoginActivity : BaseVMBActivity<LoginViewModel, ActivityLoginBinding>() {

    override fun getViewBinding(): ActivityLoginBinding = ActivityLoginBinding.inflate(layoutInflater)

    override fun initView(savedInstanceState: Bundle?) {

        mBinding.loginBtn.setOnClickListener {
            val userName = mBinding.loginUser.text.toString()
            val passWord = mBinding.loginPsd.text.toString()
            if (userName.isNotEmpty() || passWord.isNotEmpty()){
                mViewModel.login(userName, passWord){

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
