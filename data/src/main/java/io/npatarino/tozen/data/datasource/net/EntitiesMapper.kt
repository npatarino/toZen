package io.npatarino.tozen.data.datasource.net

import com.google.firebase.database.DataSnapshot
import io.npatarino.tozen.data.datasource.net.errors.NetError
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.domain.business.createTask
import io.npatarino.tozen.domain.services.task.TaskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.runSync

fun Task.toMap(): Map<String, Any> = hashMapOf("title" to title, "description" to description)

// TODO: Make this mapper more secure
@Suppress("UNCHECKED_CAST") fun DataSnapshot.toListTask(): List<Either<NetError, Task>> = children.map {
    val map = it.value as HashMap<String, String>
    val createTask: Future<Either<TaskError, Task>> = createTask(it.key!!, map["title"]!!, map["description"]!!)
    val result: Either<TaskError, Task> = createTask.runSync()
    result.bimap({ NetError.Unknown }, { it })
}