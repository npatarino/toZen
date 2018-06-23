package io.npatarino.tozen.ui.home.presenter

import io.npatarino.tozen.data.datasource.net.FirebaseDatasource
import io.npatarino.tozen.framework.domain.types.runAsync
import io.npatarino.tozen.ui.home.view.ToDoView

class ToDoPresenter(private val toDoView: ToDoView) {

    fun start() {
        FirebaseDatasource().all().runAsync {
            it.mapNotNull {
                it.getOrNull()
            }.let {
                toDoView.addTasks(it)
            }
        }
    }

}