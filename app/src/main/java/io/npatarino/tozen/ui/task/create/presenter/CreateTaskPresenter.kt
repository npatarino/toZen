package io.npatarino.tozen.ui.task.create.presenter

import io.npatarino.tozen.domain.repository.TaskRepository
import io.npatarino.tozen.domain.services.task.save
import io.npatarino.tozen.framework.domain.business.generateId
import io.npatarino.tozen.ui.task.create.view.CreateTaskView

class CreateTaskPresenter(private val view: CreateTaskView,
                          private val taskRepository: TaskRepository,
                          private val logger: (String) -> Unit) {

    fun onSaveTaskPressed(text: String) {
        logger("Save text pressed")
        view.showLoading()

        save(text, taskRepository, generateId)

    }


}