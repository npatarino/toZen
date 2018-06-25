package io.npatarino.tozen.domain.services.task

sealed class TaskError {

    object TaskNotValid : TaskError()
    object IdNotValid : TaskError()
    object TitleNotValid : TaskError()
    object DescriptionNotValid : TaskError()

}