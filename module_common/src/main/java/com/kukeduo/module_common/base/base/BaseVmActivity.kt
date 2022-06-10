package com.kukeduo.module_common.base.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.btpj.lib_base.utils.LogUtil
import com.btpj.lib_base.utils.StatusBarUtil
import com.btpj.lib_base.utils.ToastUtil
import com.kukeduo.module_common.R
import java.lang.reflect.ParameterizedType
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class BaseVMBActivity<VM : BaseViewModel, B : ViewBinding> :
    AppCompatActivity() {

    lateinit var mViewModel: VM
    lateinit var mBinding: B

    /** 是否是无状态栏的全屏模式 */
    open fun setFullScreen(): Boolean {
        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // 设置沉浸式状态栏，由于启动页SplashActivity需要无状态栏，这里写死不太好
        // 直接在主题里将其他的状态栏颜色写成跟ActionBar相同，而启动页则是无状态栏
        // 或者提供一个修改的api让SplashActivity重写，两者均可（假如需要更换主题用代码设置更灵活）
        if (setFullScreen()) {
            StatusBarUtil.setNoStatus(this)
        } else {
            StatusBarUtil.setImmersionStatus(this)
        }

        initViewModel()
        initDataBinding()
        createObserve()
        initView(savedInstanceState)
    }

    /** ViewModel初始化 */
    @Suppress("UNCHECKED_CAST")
    private fun initViewModel() {
        // 这里利用反射获取泛型中第一个参数ViewModel
        val type: Class<VM> =
            (this.javaClass.genericSuperclass as ParameterizedType).actualTypeArguments[0] as Class<VM>
        mViewModel = ViewModelProvider(this).get(type)
        mViewModel.start()
    }

    /** DataBinding初始化 */
    private fun initDataBinding() {
        mBinding = getViewBinding()
        setContentView(mBinding.root)
    }

    /** View相关初始化 */
    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun getViewBinding():B

    /** 提供编写LiveData监听逻辑的方法 */
    open fun createObserve() {
        // 全局服务器请求错误监听
        mViewModel.apply {
            exception.observe(this@BaseVMBActivity) {
                requestError(it.message)
                LogUtil.e("网络请求错误：${it.message}")
                when (it) {
                    is SocketTimeoutException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        getString(R.string.request_time_out)
                    )
                    is ConnectException, is UnknownHostException -> ToastUtil.showShort(
                        this@BaseVMBActivity,
                        getString(R.string.network_error)
                    )
                    else -> ToastUtil.showShort(
                        this@BaseVMBActivity, it.message ?: getString(R.string.response_error)
                    )
                }
            }

            // 全局服务器返回的错误信息监听
            errorResponse.observe(this@BaseVMBActivity) {
                requestError(it?.errorMsg)
                it?.errorMsg?.run {
                    ToastUtil.showShort(this@BaseVMBActivity, this)
                }
            }
        }
    }

    /** 提供一个请求错误的方法,用于像关闭加载框,显示错误布局之类的 */
    open fun requestError(msg: String?) {
//        hideLoading()
    }
}