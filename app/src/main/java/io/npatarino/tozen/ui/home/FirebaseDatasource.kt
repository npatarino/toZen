package io.npatarino.tozen.ui.home

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.npatarino.tozen.data.datasource.net.errors.NetError
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.data.Datasource
import io.npatarino.tozen.framework.data.errors.DiskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.asyncFuture
import io.npatarino.tozen.framework.domain.types.right
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.delay
import java.io.IOException
import kotlin.coroutines.experimental.Continuation
import kotlin.coroutines.experimental.suspendCoroutine


class FirebaseDatasource : Datasource<NetError, Task> {

    companion object {
        private const val TASKS = "tasks"
    }

    private val taskReference: DatabaseReference = FirebaseDatabase.getInstance().reference.child(TASKS)

    override fun all(): Future<List<Either<NetError, Task>>> = Future(async(CommonPool) {
        val snapshot: DataSnapshot = suspendCoroutine { cont: Continuation<DataSnapshot> ->
            Thread.sleep(10000)
            taskReference.addValueEventListener(object : ValueEventListener {
                override fun onCancelled(error: DatabaseError) {
                    cont.resumeWithException(IOException("Error retrieving data"))
                }

                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    cont.resume(dataSnapshot)
                }
            })
        }

        Log.e("Sync", snapshot.toString())

        listOf(Either.Left(NetError.Unknown))
    })

    //    override fun save(item: Task): Future<Either<NetError, Task>> = Future.asyncFuture {
    //        // TODO: Handle errors
    //        val key: String? = taskReference.push().key
    //        val postValues = item.toMap()
    //        val childUpdate: Map<String, Any> = hashMapOf("/$key" to postValues)
    //        taskReference.updateChildren(childUpdate)
    //        item.copy(id = key ?: "").right()
    //    }

    override fun save(item: Task): Future<Either<NetError, Task>> = Future(async(CommonPool) {
        // TODO: Handle errors
        val key: String? = taskReference.push().key
        val postValues = item.toMap()
        val childUpdate: Map<String, Any> = hashMapOf("/$key" to postValues)
        taskReference.updateChildren(childUpdate)
        item.copy(id = key ?: "").right()
    })

    override fun delete(fileName: String): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun get(fileName: String): Either<DiskError, Task> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}