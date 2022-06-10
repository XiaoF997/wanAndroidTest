package com.kukeduo.module_common.base.base

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseAdapter<T, V: ViewBinding>(list: ArrayList<T>): RecyclerView.Adapter<BaseViewHolder<V>>() {

    private var mData = list

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<V> {
        return BaseViewHolder(onBindingView(parent))
    }

    override fun onBindViewHolder(holder: BaseViewHolder<V>, position: Int) {
        onBindingData(holder, mData[position], position)
    }

    override fun getItemCount(): Int {
        return mData.size?:0
    }

    fun setList(newList: List<T>){
        mData.clear()
        mData.addAll(newList)
        notifyDataSetChanged()
    }

    fun addList(moreList: List<T>){
        val count = mData.size
        mData.addAll(moreList)
        notifyItemRangeChanged(count, mData.size)
    }

    protected abstract fun onBindingData(holder: BaseViewHolder<V>,t: T, position: Int )

    protected abstract fun onBindingView(viewGroup: ViewGroup): V

}