package com.kukeduo.kotlindemo.ui.main.project

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.loadRadius
import com.btpj.lib_base.utils.ScreenUtil
import com.btpj.wanandroid.data.bean.Article
import com.kukeduo.module_common.base.utils.ToastUtil
import com.google.android.material.tabs.TabLayout
import com.kukeduo.kotlindemo.R
import com.kukeduo.kotlindemo.bean.ProjectTitle
import com.kukeduo.kotlindemo.databinding.FragmentProjectBinding
import com.kukeduo.kotlindemo.databinding.LayoutItemHomearticleBinding
import com.kukeduo.kotlindemo.databinding.LayoutOnlytextBinding
import com.kukeduo.module_common.base.base.BaseAdapter
import com.kukeduo.module_common.base.base.BaseVMBFragment
import com.kukeduo.module_common.base.base.BaseViewHolder
import com.loper7.tab_expand.ext.buildIndicator
import com.loper7.tab_expand.ext.buildText
import com.loper7.tab_expand.ext.toPx
import com.loper7.tab_expand.indicator.LinearIndicator
import com.loper7.tab_expand.text.BaseText
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener


class ProjectFragment : BaseVMBFragment<ProjectViewModel, FragmentProjectBinding>() {

    private var curPage = 0
    private val articleData = ArrayList<Article>()

    private val pageData = ArrayList<ProjectTitle>()

    private val classAdapter by lazy {
        object : BaseAdapter<Article, LayoutItemHomearticleBinding>(articleData) {
            override fun onBindingData(
                holder: BaseViewHolder<LayoutItemHomearticleBinding>,
                t: Article,
                position: Int
            ) {
                holder.getViewBinding().apply {
                    articleTitle.text = t.title
                    articleDescript.text = t.desc
                    arcicleImg.loadRadius(
                        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg.jj20.com%2Fup%2Fallimg%2F1114%2F0FR0104212%2F200FQ04212-7-1200.jpg&refer=http%3A%2F%2Fimg.jj20.com&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=auto?sec=1657273750&t=171c41c043e2dae4e39fd8dc95d52b8e",
                        ScreenUtil.dp2px(
                            context!!, 10f
                        )
                    )
                }

            }

            override fun onBindingView(viewGroup: ViewGroup): LayoutItemHomearticleBinding {
                return LayoutItemHomearticleBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            }
        }
    }

    private val classicList:ArrayList<ProjectTitle> = ArrayList()

    companion object {
        fun newInstance() = ProjectFragment()
    }



    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectBinding =
        FragmentProjectBinding.inflate(inflater, container, false)


    override fun initView() {
        mBinding.also {bind->
            bind.projectTitle.also {
                it.tvTitleText.text = "项目"
                it.ivBack.visibility = View.INVISIBLE

            }



            bind.projectTablayout.buildText<BaseText>()
                .setNormalTextBold(false)
                .setNormalTextSize(16f)
                .setSelectTextBold(true)
                .setSelectTextSize(30f)
                .bind()


            bind.projectTablayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    ToastUtil.showShort(context!!, "$tab.position")
                    tab?.let { mViewModel.getProjectPageList(pageData[it.position].courseId) }

                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                }
            })

            bind.projectRecycle.apply {
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
                adapter = classAdapter
            }

            bind.projectRefresh.apply {
                setOnRefreshLoadMoreListener(object :OnRefreshLoadMoreListener{
                    override fun onRefresh(refreshLayout: RefreshLayout) {
                        setNoMoreData(false)
                        mViewModel.getProjectPageList(pageData[mBinding.projectTablayout.selectedTabPosition].courseId)
                    }

                    override fun onLoadMore(refreshLayout: RefreshLayout) {
                        mViewModel.getProjectPageList(pageData[mBinding.projectTablayout.selectedTabPosition].courseId, ++curPage)
                    }
                })
            }
        }


    }

    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {

            _classListData.observe(viewLifecycleOwner){
                pageData.addAll(it)
                for (value in it){
                    mBinding.projectTablayout.apply {
                        addTab(newTab().setText(value.name))
                    }
                }
            }



            _projectPageList.observe(viewLifecycleOwner){
                handleClassciPage(it)
            }



        }


    }


    private fun handleClassciPage(pageResponse: PageResponse<Article>){
        curPage = pageResponse.curPage
        val list = pageResponse.datas
        classAdapter.apply {
            if (curPage == 0){
                setList(list)
            }else{
                addList(list)
            }
        }
        if (curPage >= pageResponse.pageCount || pageResponse.size < mViewModel.pageSize){
            mBinding.projectRefresh.setNoMoreData(true)
        }
        if (curPage == 0)
            mBinding.projectRefresh.finishRefresh()
        else
            mBinding.projectRefresh.finishLoadMore()
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.projectRefresh.finishRefresh()
        mBinding.projectRefresh.finishLoadMore()

    }
}