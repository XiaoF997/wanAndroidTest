package com.kukeduo.kotlindemo.ui.main.square

import android.view.LayoutInflater
import android.view.ViewGroup
import com.kukeduo.kotlindemo.databinding.FragmentHomeBinding
import com.kukeduo.module_common.base.base.BaseVMBFragment


class SquareFragment: BaseVMBFragment<SquareViewModel, FragmentHomeBinding>() {
    override fun getViewBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    companion object{
        fun newInstance() = SquareFragment()
    }

    override fun initView() {

        mViewModel.title.observe(this){
            mBinding.biaoti.text = it
        }
    }
}