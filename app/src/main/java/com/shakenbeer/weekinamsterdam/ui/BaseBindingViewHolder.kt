package com.shakenbeer.weekinamsterdam.ui

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

import com.shakenbeer.weekinamsterdam.BR

class BaseBindingViewHolder(private val binding: ViewDataBinding,
                            private val clickListener: ClickListener)
    : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

    interface ClickListener {
        fun onViewClick(position: Int)
    }

    init {
        binding.root.setOnClickListener(this)
    }

    fun bind(obj: Any) {
        binding.setVariable(BR.obj, obj)
        binding.executePendingBindings()
    }

    override fun onClick(v: View) {
        clickListener.onViewClick(adapterPosition)
    }
}
