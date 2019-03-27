package com.shakenbeer.weekinamsterdam.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

@Suppress("unused")
abstract class BaseBindingAdapter : RecyclerView.Adapter<BaseBindingViewHolder>(), BaseBindingViewHolder.ClickListener {

    var items: MutableList<Any> = mutableListOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    private var itemClickListener: ItemClickListener<Any>? = null

    override fun onViewClick(position: Int) {
        itemClickListener?.onClick(items[position], position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseBindingViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = DataBindingUtil.inflate<ViewDataBinding>(inflater, viewType, parent, false)
        return BaseBindingViewHolder(binding, this)
    }

    override fun onBindViewHolder(holder: BaseBindingViewHolder, position: Int) {
        val obj = getObjForPosition(position)
        holder.bind(obj)
    }

    override fun getItemViewType(position: Int): Int {
        return getLayoutIdForPosition(position)
    }

    protected abstract fun getObjForPosition(position: Int): Any

    protected abstract fun getLayoutIdForPosition(position: Int): Int

    override fun getItemCount(): Int {
        return items.size
    }

    @JvmOverloads
    fun addItem(item: Any, position: Int = items.size) {
        items.add(position, item)
        notifyItemInserted(position)
    }

    @JvmOverloads
    fun addItems(itemsToAdd: List<Any>, position: Int = items.size) {
        items.addAll(position, itemsToAdd)
        notifyItemRangeInserted(position, itemsToAdd.size)
    }

    fun removeItem(position: Int) {
        items.removeAt(position)
        notifyItemRemoved(position)
    }

    fun clear() {
        val size = items.size
        if (size > 0) {
            items.clear()
            notifyItemRangeRemoved(0, size)
        }
    }

    fun setItemClickListener(itemClickListener: ItemClickListener<Any>) {
        this.itemClickListener = itemClickListener
    }

    interface ItemClickListener<in I> {
        fun onClick(item: I, position: Int)
    }
}
