package com.kukeduo.kotlindemo.ui.main.project

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import com.kukeduo.kotlindemo.bean.ProjectTitle
import com.kukeduo.kotlindemo.databinding.FragmentHomeBinding
import com.kukeduo.kotlindemo.databinding.FragmentProjectBinding
import com.kukeduo.kotlindemo.databinding.LayoutItemHomearticleBinding
import com.kukeduo.kotlindemo.databinding.LayoutOnlytextBinding
import com.kukeduo.module_common.base.base.BaseAdapter
import com.kukeduo.module_common.base.base.BaseVMBFragment
import com.kukeduo.module_common.base.base.BaseViewHolder


class ProjectFragment : BaseVMBFragment<ProjectViewModel, FragmentProjectBinding>() {
    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentProjectBinding =
        FragmentProjectBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = ProjectFragment()
    }

    val classAdapter by lazy {
        object :BaseAdapter<ProjectTitle, LayoutOnlytextBinding>(ArrayList()){
            override fun onBindingData(
                holder: BaseViewHolder<LayoutOnlytextBinding>,
                t: ProjectTitle,
                position: Int
            ) {
                holder.getViewBinding().itemT.also {
                    it.text = t.name
                    it.gravity = Gravity.CENTER
                }
            }

            override fun onBindingView(viewGroup: ViewGroup): LayoutOnlytextBinding {
                return LayoutOnlytextBinding.inflate(
                    LayoutInflater.from(viewGroup.context),
                    viewGroup,
                    false
                )
            }

        }
//        object :android.widget.BaseAdapter(){
//            private var  data = ArrayList<ProjectTitle>()
//            override fun getCount(): Int = data.size
//
//            override fun getItem(position: Int): ProjectTitle {
//               return data.get(position)
//            }
//
//            override fun getItemId(position: Int): Long {
//                return 0
//            }
//
//            fun setData(newList: List<ProjectTitle>){
//                data.clear()
//                data.addAll(newList)
//                notifyDataSetChanged()
//            }
//
//            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
//                val t :TextView
//                if (convertView == null){
//                   t = TextView(context)
//                    t.gravity = Gravity.CENTER
//                }else{
//                    t = convertView as TextView
//                }
//                t.text = data[position].name
//                return t
//            }
//        }
    }

    override fun initView() {
        mBinding.also {bind->
            bind.projectTitle.also {
                it.tvTitleText.text = "项目"
                it.ivBack.visibility = View.INVISIBLE

            }

            bind.textView.text = "分类"

            bind.projectClassrec.also {
                it.layoutManager = GridLayoutManager(context, 4)
                it.adapter = classAdapter
            }
        }

        mViewModel._classListData.observe(viewLifecycleOwner){
            mViewModel._classListData.value?.let { it1 -> classAdapter.setList(it1) }
        }
    }

}