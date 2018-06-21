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
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.domain.repository.TaskRepository
import io.npatarino.tozen.domain.repository.error.RepositoryError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.ui.task.create.presenter.CreateTaskPresenter
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.content_create_task.*
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.toast

class CreateTask : AppCompatActivity(), CreateTaskView {

    private lateinit var presenter: CreateTaskPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CreateTaskPresenter(this, object : TaskRepository {
            override fun save(task: Task): Future<Either<RepositoryError, Task>> = Future(async(CommonPool) {
                toast(task.toString()).show()
                Either.Right(task)
            })
        }, { Log.e(this.javaClass.name, it) }) // TODO: Make logging more functional
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
            val text = editTextTitle.text.toString()
            presenter.onSaveTaskPressed(text)
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
}
