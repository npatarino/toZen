package io.npatarino.tozen.domain.services.task

import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.domain.business.createTask
import io.npatarino.tozen.framework.data.repository.Repository
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.map
import io.npatarino.tozen.framework.domain.types.pure
import io.npatarino.tozen.framework.domain.types.runSync

fun save(taskTitle: String,
         taskDescription: String,
         taskRepository: Repository<Task>,
         generateId: () -> String): Future<Either<TaskError, Task>> {
    val id = generateId()
    val task = createTask(id, taskTitle, taskDescription).runSync()
    task.map {
        return taskRepository.save(it).map {
            it.bimap({ TaskError.TaskNotValid }, { it })
        }
    }
    return Future.pure(task)
}

fun all(taskRepository: Repository<Task>) = taskRepository.all()