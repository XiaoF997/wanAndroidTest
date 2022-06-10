package com.kukeduo.kotlindemo.ui.register

import android.content.Context
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.res.ResourcesCompat
import com.btpj.lib_base.ext.load
import com.btpj.lib_base.utils.ToastUtil
import com.kukeduo.kotlindemo.R
import com.kukeduo.kotlindemo.databinding.ActivityRegisterBinding
import com.kukeduo.module_common.base.base.BaseVMBActivity

class RegisterActivity : BaseVMBActivity<RegisterViewModel, ActivityRegisterBinding>() {

    override fun getViewBinding(): ActivityRegisterBinding = ActivityRegisterBinding.inflate(layoutInflater)

    companion object{
        fun launch(context: Context){
            context.startActivity(Intent(context, RegisterActivity::class.java))
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mBinding.also {
            it.registerRqcode.load("https://www.wanandroid.com/resources/image/pc/weixin_hongyang.jpg")

            it.registerBtn.setOnClickListener {
                val userName = mBinding.registerUser.text.toString()
                val pwd = mBinding.registerPwd.text.toString()
                val surePassWord = mBinding.registerSurePsd.text.toString()
                if (userName.isNotEmpty() && pwd.isNotEmpty() && surePassWord.isNotEmpty() && pwd == surePassWord){
                    mViewModel.register(userName, pwd, surePassWord){
                        ToastUtil.showShort(this, "注册成功")
                        finish()
                    }
                }
            }

            it.registerTitle.clTitleBar.background = ResourcesCompat.getDrawable(resources, R.color.white, null)

            it.registerTitle.tvTitleText.apply {
                text = "注册"
                setTextColor(ResourcesCompat.getColor(resources, R.color.black, null))
            }

            val drawable = it.registerTitle.ivBack.drawable

            drawable.setTint(Color.BLACK)

            it.registerTitle.ivBack.setOnClickListener {
                finish()
            }
        }


    }


}