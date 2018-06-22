package io.npatarino.tozen.data.datasource.net

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.npatarino.tozen.data.datasource.net.errors.NetError
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.data.datasource.Datasource
import io.npatarino.tozen.framework.data.datasource.errors.DiskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.asyncFuture
import io.npatarino.tozen.framework.domain.types.right
import kotlinx.coroutines.experimental.async
import java.io.IOException
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine

class FirebaseDatasource : Datasource<NetError, Task> {

    companion object {
        private const val TASKS = "tasks"
    }

    private val taskReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(TASKS)

    override fun all(): Future<List<Either<NetError, Task>>> = Future(async {
        suspendCoroutine { cont: Continuation<List<Either<NetError, Task>>> ->
            taskReference.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    cont.resumeWithException(IOException("Error retrieving data"))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    cont.resume(dataSnapshot.toListTask())
                }
            })
        }
    })

    override fun save(item: Task): Future<Either<NetError, Task>> = Future.asyncFuture {
        // TODO: Handle errors
        val key: String? = taskReference.push().key
        val postValues = item.toMap()
        val childUpdate: Map<String, Any> = hashMapOf("/$key" to postValues)
        taskReference.updateChildren(childUpdate)
        item.copy(id = key ?: "").right()
    }

    override fun delete(fileName: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(fileName: String): Either<DiskError, Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}