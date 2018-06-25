package io.npatarino.tozen.ui.task.create.view

interface CreateTaskView {

    fun showLoading()
    fun finish()
    fun showTaskTitleNotValidError()
    fun showTaskDescriptionNotValidError()

}