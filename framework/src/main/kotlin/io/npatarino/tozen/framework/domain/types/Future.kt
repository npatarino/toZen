package io.npatarino.tozen.framework.domain.types

import kotlinx.coroutines.experimental.*

class Future<out A>(val task: Deferred<A>) {
    companion object
}

fun <A> Future.Companion.pure(a: A): Future<A> =
    Future(async(CommonPool) { a })

fun <A> asyncFuture(getValue: () -> A): Future<A> =
    Future(async(CommonPool) {
        getValue()
    })

fun <A> Future<A>.runAsync(onComplete: (A) -> Unit) {
    launch(CommonPool) {
        onComplete(task.await())
    }
}

fun <A> Future<A>.runSync(): A =
    runBlocking { this@runSync.task.await() }

fun <A, B> Future<A>.map(transform: (A) -> B): Future<B> =
    flatMap {
        Future(async(CommonPool) {
            transform(it)
        })
    }

fun <A, B> Future<A>.flatMap(transform: (A) -> Future<B>): Future<B> =
    Future(async(CommonPool) {
        transform(this@flatMap.task.await()).task.await()
    })

fun <A, B> Future<A>.apply(futureAB: Future<(A) -> B>): Future<B> =
    Future(async(CommonPool) {
        val a = this@apply.task.await()
        val ab = futureAB.task.await()

        ab(a)
    })