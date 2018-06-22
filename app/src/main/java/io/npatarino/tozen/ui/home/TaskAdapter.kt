package io.npatarino.tozen.ui.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.npatarino.tozen.R
import io.npatarino.tozen.domain.business.Task
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(private val items: MutableList<Task>,
                  private val context: Context) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskAdapter.ViewHolder =
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_task, parent, false))

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: TaskAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(task: Task) = with(itemView) {
            title.text = task.title
        }
    }

    fun add(itemsToAdd: List<Task>) {
        items.addAll(itemsToAdd)
    }

}