package com.kukeduo.kotlindemo.ui.main.wechat

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kukeduo.kotlindemo.databinding.FragmentHomeBinding
import com.kukeduo.module_common.base.base.BaseVMBFragment


class WechatFragment: BaseVMBFragment<WeChatViewModel, FragmentHomeBinding>() {
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)


    companion object{
        fun newInstance() = WechatFragment()
    }

    override fun initView() {

        mViewModel.title.observe(this){
            mBinding.biaoti.text = it
        }
    }
}