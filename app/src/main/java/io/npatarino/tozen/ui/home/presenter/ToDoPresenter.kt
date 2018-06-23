package io.npatarino.tozen.ui.home.presenter

import io.npatarino.tozen.data.datasource.net.FirebaseDatasource
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.domain.services.task.all
import io.npatarino.tozen.framework.data.repository.Repository
import io.npatarino.tozen.framework.data.repository.error.RepositoryError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.runAsync
import io.npatarino.tozen.ui.home.view.ToDoView

class ToDoPresenter(private val toDoView: ToDoView, private val repository: Repository<Task>) {

    fun start() {
        all(repository).runAsync {
            it.fold({ toDoView.showError() }, { toDoView.addTasks(it) })
        }
    }

    fun resume() {

    }

}