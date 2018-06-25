package io.npatarino.tozen.framework.domain.types

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.async
import java.util.*

sealed class Either<out L, out R> {

    companion object {

    }

    data class Left<out T>(val value: T) : Either<T, Nothing>() {
        override fun isRight(): Boolean = false
        override fun isLeft(): Boolean = true
        override fun get(): Nothing = throw NoSuchElementException("Either.Right.value on Left")
    }

    data class Right<out T>(val value: T) : Either<Nothing, T>() {
        override fun isRight(): Boolean = true
        override fun isLeft(): Boolean = false
        override fun get(): T = value
    }

    abstract fun isLeft(): Boolean
    abstract fun isRight(): Boolean
    abstract fun get(): R

    /**
     * Example:
     * ```
     * val result: Either<Error, Value> = someMethod()
     * result.bimap(
     *      { "Error" },
     *      { "Success with $it" }
     * )
     * ```
     */
    inline fun <C, P> bimap(left: (L) -> C, right: (R) -> P): Either<C, P> =
            fold({ Left(left(it)) }, { Right(right(it)) })

    /**
     * Example:
     * ```
     * val result: Either<Error, Value> = someMethod()
     * result.fold(
     *      { log("Error with $it") },
     *      { log("Success with $it") }
     * )
     * ```
     */
    inline fun <C> fold(left: (L) -> C, right: (R) -> C): C = when (this) {
        is Left  -> left(value)
        is Right -> right(value)
    }

    /**
     * Example:
     * ```
     * Left(error).swap()   // Result: Right("Maradona")
     * Right("Maradona").swap() // Result: Left(error)
     * ```
     */
    fun swap() = fold({ Right(it) }, { Left(it) })

    /**
     * Example:
     * ```
     * Right("Maradona").map { "Diego" } // Result: Right("Diego")
     * Left(error).map { "Diego" }  // Result: Left(error)
     * ```
     */
    inline fun <C> map(f: (R) -> C): Either<L, C> = fold({ Left(it) }, { Right(f(it)) })

    /**
     * Example:
     * ```
     * Right("Maradona").getOrNull() // Result: "Maradona"
     * Left(error).getOrNull()  // Result: null
     * ```
     */
    fun getOrNull(): R? = fold({ null }, { it })

    /**
     * Returns the value from this [Either.Right] or the given argument if this is a [Either.Left].
     *
     * Example:
     * ```
     * Right("Maradona").getOrElse("undefined") // Result: "Maradona"
     * Left(error).getOrElse("undefined")  // Result: "undefined"
     * ```
     */
    fun <B> Either<*, B>.getOrElse(default: () -> B): B = fold({ default() }, { it })
}

fun <L, R, S> Either<L, R>.flatMap(f: (R) -> Either<L, S>): Either<L, S> = fold({ Either.Left(it) }, { f(it) })

fun <L> L.left() = Either.Left(this)
fun <R> R.right() = Either.Right(this)

fun <R> Either.Companion.pure(value: R): Either.Right<R> = Either.Right(value)

fun <L, R, C> Either<L, R>.apply(resultAB: Either<L, (R) -> C>): Either<L, C> = flatMap { a -> resultAB.map { it(a) } }

infix fun <L, R, C> Future<Either<L, (R) -> C>>.ap(asyncResult: Future<Either<L, R>>): Future<Either<L, C>> =
        Future(async(CommonPool) {
            val resultRC: Either<L, (R) -> C> = this@ap.task.await()
            val resultR: Either<L, R> = asyncResult.task.await()
            resultR.apply(resultRC)
        })

infix fun <L, R, C> Future<Either<L, R>>.bind(transform: (R) -> Future<Either<L, C>>): Future<Either<L, C>> =
        this.flatMap {
            when (it) {
                is Either.Right -> transform(it.value)
                is Either.Left  -> Future.pure(it)
            }
        }