package com.kukeduo.kotlindemo.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.btpj.lib_base.data.bean.PageResponse
import com.btpj.lib_base.ext.load
import com.btpj.lib_base.ext.loadRadius
import com.btpj.lib_base.utils.ScreenUtil
import com.btpj.wanandroid.data.bean.Article
import com.kukeduo.kotlindemo.bean.BannerBean
import com.kukeduo.kotlindemo.databinding.FragmentHomeBinding
import com.kukeduo.kotlindemo.databinding.LayoutItemHomearticleBinding
import com.kukeduo.module_common.base.base.BaseAdapter
import com.kukeduo.module_common.base.base.BaseVMBFragment
import com.kukeduo.module_common.base.base.BaseViewHolder
import com.scwang.smartrefresh.layout.api.RefreshLayout
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener
import com.youth.banner.adapter.BannerImageAdapter
import com.youth.banner.holder.BannerImageHolder


class HomeFragment : BaseVMBFragment<HomeViewModel, FragmentHomeBinding>() {

    private val articleData = ArrayList<Article>()

    private var curPage = 0


    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentHomeBinding =
        FragmentHomeBinding.inflate(inflater, container, false)

    private val mArticleAdapter by lazy {
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

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun initView() {
        mBinding.apply {
//            homeArciclerecyc.apply {
            recyclerView.apply {
                adapter = mArticleAdapter
                layoutManager = LinearLayoutManager(context).apply {
                    orientation = LinearLayoutManager.VERTICAL
                }
//                }
            }
            smartRefreshLayout.setOnRefreshLoadMoreListener(object : OnRefreshLoadMoreListener {
                override fun onRefresh(refreshLayout: RefreshLayout) {
                    mViewModel.getArticlePageList()
                    smartRefreshLayout.setNoMoreData(false)
                }

                override fun onLoadMore(refreshLayout: RefreshLayout) {
                    mViewModel.getArticlePageList(curPage++)
                }

            })
        }
    }


    override fun createObserve() {
        super.createObserve()
        mViewModel.apply {
            title.observe(viewLifecycleOwner) {
                mBinding.biaoti.text = it
            }

            bannerListLiveData.observe(viewLifecycleOwner) {
                mBinding.homeBanner.setAdapter(object : BannerImageAdapter<BannerBean>(it) {
                    override fun onBindView(
                        holder: BannerImageHolder?,
                        data: BannerBean?,
                        position: Int,
                        size: Int
                    ) {
                        data?.imagePath?.let { it1 -> holder?.imageView?.load(it1) }
                    }

                })
            }

            articlePageListData.observe(viewLifecycleOwner) {
                handleHomeArticleAdapter(it)
//                mArticleAdapter.notifyDataSetChanged()
            }
        }
    }

    fun handleHomeArticleAdapter(pageData: PageResponse<Article>) {
        curPage = pageData.curPage
        val list = pageData.datas
        mArticleAdapter.apply {
            if (curPage == 1) {
                setList(list)
            } else {
                addList(list)
            }
        }

        if (curPage >= pageData.pageCount || list.size < mViewModel.pageSize) {
            //数据全部加载完毕之后显示没有更多数据

            mBinding.smartRefreshLayout.setNoMoreData(true)
//            mBinding.smartRefreshLayout.setEnableLoadMore(false)
        }
        if (curPage == 1)
        //如果是第一页的话  不是第一次加载就是刷新 只需关闭刷新窗
            mBinding.smartRefreshLayout.finishRefresh()
        else
            mBinding.smartRefreshLayout.finishLoadMore()
    }

    override fun requestError(msg: String?) {
        super.requestError(msg)
        mBinding.smartRefreshLayout.finishRefresh()
        mBinding.smartRefreshLayout.finishLoadMore()

    }

}