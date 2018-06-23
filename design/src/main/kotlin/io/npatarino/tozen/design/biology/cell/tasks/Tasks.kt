package io.npatarino.tozen.design.biology.cell.tasks

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.util.Log
import android.widget.LinearLayout
import io.npatarino.tozen.R
import io.npatarino.tozen.design.biology.cell.tasks.model.TaskModel
import io.npatarino.tozen.design.extensions.uiFuture
import io.npatarino.tozen.design.recycler.adapter.Adapter
import io.npatarino.tozen.design.recycler.adapter.setUp
import io.npatarino.tozen.framework.domain.types.Future
import kotlinx.android.synthetic.main.cell_task_minimal.view.*

class Tasks : LinearLayout {

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(context,
                                                                                                            attrs,
                                                                                                            defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) constructor(context: Context,
                                                         attrs: AttributeSet?,
                                                         defStyleAttr: Int,
                                                         defStyleRes: Int) : super(context,
                                                                                   attrs,
                                                                                   defStyleAttr,
                                                                                   defStyleRes)

    private val adapter: Adapter<TaskModel>

    init {
        orientation = VERTICAL
        val recyclerView = RecyclerView(context)
        recyclerView.layoutManager = LinearLayoutManager(context)
        adapter = recyclerView.setUp(emptyList(), R.layout.cell_task_minimal, {
            taskTitle.text = it.title
        }, { Log.e("Example", "Example") })
        addView(recyclerView)
    }

    fun render(tasks: List<TaskModel>) = Future.uiFuture {
        adapter.add(tasks)
    }

}
