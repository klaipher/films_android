package com.example.films.adapter

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.films.util.ViewSize

abstract class MultipleViewSizeAdapter<T> : RecyclerView.Adapter<MultipleViewSizeAdapter.BaseViewHolder<T>>() {
    var viewSize: ViewSize = ViewSize.SMALL
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    abstract  class BaseViewHolder<T>(
        binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        abstract fun bind(items: T)
    }
    abstract fun addItems(items: List<T>)
}