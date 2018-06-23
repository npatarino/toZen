package io.npatarino.tozen.design.recycler.adapter

import android.support.v7.util.DiffUtil

internal class DiffUtilCallback<Item>(private val oldItems: List<Item>,
                                      private val newItems: List<Item>) : DiffUtil.Callback() {

    override fun getOldListSize() = oldItems.size

    override fun getNewListSize() = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldItems[oldItemPosition] == newItems[newItemPosition]

}
