package io.npatarino.tozen.data.datasource.net

import com.google.firebase.database.DataSnapshot
import io.npatarino.tozen.data.datasource.net.errors.NetError
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.right

fun Task.toMap(): Map<String, Any> = hashMapOf("title" to title)

fun DataSnapshot.toListTask(): List<Either<NetError, Task>> = children.map {
    Task(it.key!!, (it.value as HashMap<String, String>)["title"]!!).right()
}