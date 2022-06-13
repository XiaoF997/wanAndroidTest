package com.kukeduo.kotlindemo.ui.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.kukeduo.module_common.base.utils.ToastUtil
import com.kukeduo.kotlindemo.R
import com.kukeduo.kotlindemo.databinding.ActivityMainBinding
import com.kukeduo.kotlindemo.ext.clearLongClickToast
import com.kukeduo.kotlindemo.ui.main.home.HomeFragment
import com.kukeduo.kotlindemo.ui.main.mine.MineFragment
import com.kukeduo.kotlindemo.ui.main.project.ProjectFragment
import com.kukeduo.kotlindemo.ui.main.square.SquareFragment
import com.kukeduo.kotlindemo.ui.main.wechat.WechatFragment
import com.kukeduo.module_common.base.base.BaseVMBActivity

class Main2Activity: BaseVMBActivity<Main2ViewModel, ActivityMainBinding>() {

    private val mHomeFragment by lazy { HomeFragment.newInstance() }
    private val mMineFragment by lazy { MineFragment.newInstance() }
    private val mProjectFragment by lazy { ProjectFragment.newInstance() }
    private val mSquareFragment by lazy { SquareFragment.newInstance() }
    private val mWechatFragment by lazy { WechatFragment.newInstance() }

    private var mCurrentFragment: Fragment = mHomeFragment
    private var mCurrentNavPosition = 0

    companion object {
        /** 记录修改配置（如页面旋转）前navBar的位置的常量 */
//        private const val CURRENT_NAV_POSITION = "currentNavPosition"

        /** 跳转 */
        fun launch(context: Context?) {
            context?.startActivity(Intent(context, Main2Activity::class.java))
        }
    }

    override fun getViewBinding(): ActivityMainBinding = ActivityMainBinding.inflate(layoutInflater)


    override fun initView(savedInstanceState: Bundle?) {
        supportFragmentManager.beginTransaction().add(R.id.fl_container, mHomeFragment, "HomeFragment").commitAllowingStateLoss()

        mBinding.bottomNavigationView.apply {
            clearLongClickToast(
                mutableListOf(
                    R.id.menu_home,
                    R.id.menu_project,
                    R.id.menu_square,
                    R.id.menu_wechat,
                    R.id.menu_mine
                )
            )
            setOnNavigationItemSelectedListener {

                return@setOnNavigationItemSelectedListener onNavBarItemSelected(it.itemId)
            }
        }

    }

    /**
     * bottomNavigationView切换时调用的方法
     *
     * @param itemId 切换Item的id
     */
    private fun onNavBarItemSelected(itemId: Int): Boolean {
        when (itemId) {
            R.id.menu_home -> {
                mCurrentNavPosition = 0
                switchFragment(mHomeFragment)
                return true
            }
            R.id.menu_project -> {
                mCurrentNavPosition = 1
                switchFragment(mProjectFragment)
                return true
            }
            R.id.menu_square -> {
                mCurrentNavPosition = 2
                switchFragment(mSquareFragment)
                return true
            }
            R.id.menu_wechat -> {
                mCurrentNavPosition = 3
                switchFragment(mWechatFragment)
                return true
            }
            else -> {
                mCurrentNavPosition = 4
                switchFragment(mMineFragment)
                return true
            }
        }
    }

    /**
     * 切换Fragment
     *
     * @param fragment 要切换的Fragment
     */
    private fun switchFragment(fragment: Fragment) {
        if (fragment !== mCurrentFragment) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            // 先隐藏当前显示的Fragment
            fragmentTransaction.hide(mCurrentFragment)
            if (!fragment.isAdded) {
                // 存入Tag,以便获取，解决界面重叠问题 参考http://blog.csdn.net/showdy/article/details/50825800
                fragmentTransaction.add(R.id.fl_container, fragment, fragment.javaClass.simpleName)
                    .show(fragment)
            } else {
                fragmentTransaction.show(fragment)
            }
            // 执行提交
            fragmentTransaction.commitAllowingStateLoss()
            // 将当前Fragment赋值为切换后的Fragment
            mCurrentFragment = fragment
        }
    }


//    private lateinit var binding: ActivityMainBinding
//
//    private val vm by viewModels<Main2ViewModel>()
////    private val vm :Main2ViewModel by viewmodels()
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityMainBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//
//        vm.text.value = "打折"
//        binding.btn.setOnClickListener {
////            vm.getQueryResult("心","e08b373b08bf9e287f4861d2f1894664")
////            Toast.makeText(this, "asd", Toast.LENGTH_SHORT).show()
//            vm.getHomePage(1)
//        }
//
//        vm.text.observe(this){
//            binding.result.text = it
//        }
//
//    }

    /** 上一次点击返回键的时间 */
    private var lastBackMills: Long = 0

    override fun onBackPressed() {
        // 重写返回键监听实现双击退出
        if (System.currentTimeMillis() - lastBackMills > 2000) {
            lastBackMills = System.currentTimeMillis()
            ToastUtil.showShort(this, getString(R.string.toast_double_back_exit))
        } else {
            super.onBackPressed()
        }
    }
}