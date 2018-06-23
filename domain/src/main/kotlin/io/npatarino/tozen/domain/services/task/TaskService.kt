package io.npatarino.tozen.domain.services.task

import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.data.repository.Repository
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.map

typealias FutureTask = Future<Either<TaskError, Task>>

fun save(taskTitle: String, taskRepository: Repository<Task>, generateId: () -> String): FutureTask {
    val id = generateId()
    val task = Task(id, taskTitle)
    return taskRepository.save(task).map {
        it.bimap({ TaskError.TaskNotValid(task) }, { it })
    }
}

fun all(taskRepository: Repository<Task>) = taskRepository.all()