package com.kukeduo.module_common.base.base

import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

class BaseViewHolder<V: ViewBinding>(private val v:V):RecyclerView.ViewHolder(v.root) {

    fun getViewBinding(): V = v

}