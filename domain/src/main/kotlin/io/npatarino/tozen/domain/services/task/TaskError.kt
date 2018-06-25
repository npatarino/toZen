package io.npatarino.tozen.domain.services.task

import io.npatarino.tozen.domain.business.Task

sealed class TaskError {

    class TaskNotValid(task: Task) : TaskError()
    object TitleNotValid : TaskError()

}