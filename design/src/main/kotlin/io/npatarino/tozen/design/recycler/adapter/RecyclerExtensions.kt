package io.npatarino.tozen.design.recycler.adapter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View

fun <Item> RecyclerView.setUp(items: List<Item>,
                              layoutResId: Int,
                              bindHolder: View.(Item) -> Unit,
                              itemClick: Item.() -> Unit = {},
                              manager: RecyclerView.LayoutManager = LinearLayoutManager(this.context)): Adapter<Item> {

    val recyclerAdapter by lazy {
        Adapter(items, layoutResId, { bindHolder(it) }, { itemClick() })
    }

    layoutManager = manager
    adapter = recyclerAdapter
    return recyclerAdapter
}
