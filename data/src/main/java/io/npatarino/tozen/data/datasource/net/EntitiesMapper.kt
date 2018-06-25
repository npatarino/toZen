package io.npatarino.tozen.data.datasource.net

import com.google.firebase.database.DataSnapshot
import io.npatarino.tozen.data.datasource.net.errors.NetError
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.right

fun Task.toMap(): Map<String, Any> = hashMapOf("title" to title, "description" to description)

// TODO: Make this mapper more secure
@Suppress("UNCHECKED_CAST") fun DataSnapshot.toListTask(): List<Either<NetError, Task>> = children.map {
    val map = it.value as HashMap<String, String>
    Task(it.key!!, map["title"]!!, map["description"]!!).right()
}