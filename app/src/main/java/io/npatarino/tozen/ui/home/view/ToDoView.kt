package io.npatarino.tozen.ui.home.view

import io.npatarino.tozen.domain.business.Task

interface ToDoView {

    fun addTasks(items: List<Task>)
    fun showError()
}