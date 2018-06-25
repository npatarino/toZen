package io.npatarino.tozen.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import io.npatarino.tozen.R
import io.npatarino.tozen.data.datasource.net.FirebaseDatasource
import io.npatarino.tozen.data.repository.TaskRepository
import io.npatarino.tozen.design.biology.cell.tasks.model.TaskModel
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.data.datasource.local.DiskDatasource
import io.npatarino.tozen.framework.data.datasource.local.MoshiJsonConverter
import io.npatarino.tozen.framework.data.datasource.local.ReadableFolder
import io.npatarino.tozen.framework.domain.business.generateId
import io.npatarino.tozen.framework.domain.types.runAsync
import io.npatarino.tozen.ui.home.HomeViewPagerAdapter
import io.npatarino.tozen.ui.home.presenter.ToDoPresenter
import io.npatarino.tozen.ui.task.create.view.CreateTask
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_pager_todo.*
import java.io.File

class Main : AppCompatActivity(), ToDoView {

    private lateinit var toDoPresenter: ToDoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)

        val diskDatasource =
                DiskDatasource(ReadableFolder(File("tasks")), MoshiJsonConverter(Task::class.java), generateId)
        val taskRepository = TaskRepository(FirebaseDatasource(), diskDatasource)

        toDoPresenter = ToDoPresenter(this, taskRepository)

        viewPager.adapter = HomeViewPagerAdapter(this)

        if (viewPager.currentItem == 0) {
            toDoPresenter.start()
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_add -> {
            startActivity(Intent(this@Main, CreateTask::class.java))
            true
        }
        else          -> super.onOptionsItemSelected(item)
    }

    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home          -> {
                viewPager.currentItem = 0

                toDoPresenter.resume()

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard     -> {
                viewPager.currentItem = 1
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                viewPager.currentItem = 2
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun showError() {

    }

    override fun addTasks(items: List<Task>) {
        tasks.render(items.map { TaskModel(it.id, it.title) }).runAsync { }
    }
}
