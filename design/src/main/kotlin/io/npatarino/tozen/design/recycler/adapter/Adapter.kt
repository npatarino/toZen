package io.npatarino.tozen.design.recycler.adapter

import android.view.View

class Adapter<Item>(items: List<Item>,
                    layoutResId: Int,
                    private val bindHolder: View.(Item) -> Unit,
                    private val click: Item.() -> Unit = {})
    : StubAdapter<Item>(items, layoutResId) {

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.itemView.bindHolder(items[position])
    }

    override fun onItemClick(itemView: View, position: Int) {
        items[position].click()
    }

}
