package io.npatarino.tozen.design.recycler.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import io.npatarino.tozen.design.extensions.inflate

abstract class StubAdapter<Item> constructor(items: List<Item>,
                                             private val layoutResId: Int) : RecyclerView.Adapter<StubAdapter.Holder>() {

    protected val items = items.toMutableList()

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = parent.inflate(layoutResId)
        val viewHolder = Holder(view)
        val itemView = viewHolder.itemView
        itemView.setOnClickListener {
            val adapterPosition = viewHolder.adapterPosition
            if (adapterPosition != RecyclerView.NO_POSITION) {
                onItemClick(itemView, adapterPosition)
            }
        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = items[position]
        holder.itemView.bind(item)
    }

    final override fun onViewRecycled(holder: Holder) {
        super.onViewRecycled(holder)
        onViewRecycled(holder.itemView)
    }

    protected open fun onViewRecycled(itemView: View) {
    }

    protected open fun onItemClick(itemView: View, position: Int) {
    }

    protected open fun View.bind(item: Item) {
    }

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView)

    fun add(item: Item) {
        items.add(item)
        notifyItemInserted(items.size)
    }

    fun remove(position: Int) {
        items.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun update(items: List<Item>) {
        updateAdapterWithDiffResult(calculateDiff(items))
    }

    private fun calculateDiff(newItems: List<Item>) =
            android.support.v7.util.DiffUtil.calculateDiff(DiffUtilCallback(items, newItems))

    private fun updateAdapterWithDiffResult(result: DiffUtil.DiffResult) {
        result.dispatchUpdatesTo(this)
    }
}

