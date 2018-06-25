package io.npatarino.tozen.ui.task.create.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ProgressBar
import android.widget.RelativeLayout
import io.npatarino.tozen.R
import io.npatarino.tozen.data.datasource.net.FirebaseDatasource
import io.npatarino.tozen.data.repository.TaskRepository
import io.npatarino.tozen.design.extensions.toast
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.data.datasource.local.DiskDatasource
import io.npatarino.tozen.framework.data.datasource.local.MoshiJsonConverter
import io.npatarino.tozen.framework.data.datasource.local.ReadableFolder
import io.npatarino.tozen.framework.domain.business.generateId
import io.npatarino.tozen.ui.task.create.presenter.CreateTaskPresenter
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.content_create_task.*
import java.io.File

class CreateTask : AppCompatActivity(), CreateTaskView {
    private lateinit var presenter: CreateTaskPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val diskDatasource =
                DiskDatasource(ReadableFolder(File("tasks")), MoshiJsonConverter(Task::class.java), generateId)

        presenter = CreateTaskPresenter(this, TaskRepository(FirebaseDatasource(), diskDatasource), {
            Log.e(this.javaClass.name, it)
        }) // TODO: Make logging more functional
        setContentView(R.layout.activity_create_task)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_create_task, menu) //your file name
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_save -> {
            val title = editTextTitle.text.toString()
            val description = editTextDescription.text.toString()
            presenter.onSaveTaskPressed(title, description)
            true
        }
        else           -> super.onOptionsItemSelected(item)
    }

    override fun showLoading() {
        val progressBar = ProgressBar(this@CreateTask, null, android.R.attr.progressBarStyleLarge)
        val params = RelativeLayout.LayoutParams(100, 100)
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        rootContent.addView(progressBar, params)
        progressBar.visibility = View.VISIBLE
    }

    override fun showTaskTitleNotValidError() {
        toast("Title not valid")
    }

    override fun showTaskDescriptionNotValidError() {
        toast("Description not valid")
    }
}
