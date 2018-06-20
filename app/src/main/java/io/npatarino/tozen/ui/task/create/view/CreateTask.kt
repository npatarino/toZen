package io.npatarino.tozen.ui.task.create.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.text.Editable
import android.view.Menu
import android.view.MenuItem
import io.npatarino.tozen.R
import io.npatarino.tozen.ui.task.create.presenter.CreateTaskPresenter
import kotlinx.android.synthetic.main.activity_create_task.*
import kotlinx.android.synthetic.main.content_create_task.*

class CreateTask : AppCompatActivity(), CreateTaskView {

    private lateinit var presenter: CreateTaskPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        presenter = CreateTaskPresenter(this)
        setContentView(R.layout.activity_create_task)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_create_task, menu) //your file name
        return super.onCreateOptionsMenu(menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean = when (item.itemId) {
        R.id.menu_add -> {
            val text = editTextTitle.text.toString()
            presenter.saveTask(text)
            true
        }
        else          -> super.onOptionsItemSelected(item)
    }
}
