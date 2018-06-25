package io.npatarino.tozen.ui.task.create.presenter

import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.domain.services.task.TaskError
import io.npatarino.tozen.domain.services.task.save
import io.npatarino.tozen.framework.data.repository.Repository
import io.npatarino.tozen.framework.domain.business.generateId
import io.npatarino.tozen.framework.domain.types.runAsync
import io.npatarino.tozen.ui.task.create.view.CreateTaskView

class CreateTaskPresenter(private val view: CreateTaskView,
                          private val taskRepository: Repository<Task>,
                          private val logger: (String) -> Unit) {

    fun onSaveTaskPressed(text: String, description: String) {
        logger("Save text pressed")
        view.showLoading()

        save(text, description, taskRepository, generateId).runAsync {
            it.fold({
                        when (it) {
                            is TaskError.TaskNotValid -> view.showTaskTitleNotValidError()
                            TaskError.TitleNotValid   -> view.showTaskDescriptionNotValidError()
                        }
                    }, {
                view.finish()
            })
        }
    }


}