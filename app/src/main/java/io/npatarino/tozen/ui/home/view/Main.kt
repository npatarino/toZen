package io.npatarino.tozen.ui.home.view

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.npatarino.tozen.R
import io.npatarino.tozen.design.biology.cell.tasks.model.TaskModel
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.domain.types.runAsync
import io.npatarino.tozen.ui.home.HomeViewPagerAdapter
import io.npatarino.tozen.ui.home.presenter.ToDoPresenter
import io.npatarino.tozen.ui.task.create.view.CreateTask
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.home_pager_todo.*

class Main : AppCompatActivity(), ToDoView {

    private lateinit var toDoPresenter: ToDoPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        toDoPresenter = ToDoPresenter(this)

        viewPager.adapter = HomeViewPagerAdapter(this)
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

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home          -> {
                viewPager.currentItem = 0

                toDoPresenter.start()



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

    override fun addTasks(items: List<Task>) {
        tasks.render(items.map { TaskModel(it.id, it.title) }).runAsync {  }
    }
}
