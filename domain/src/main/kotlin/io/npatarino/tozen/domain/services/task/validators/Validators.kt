package io.npatarino.tozen.domain.services.task.validators

import io.npatarino.tozen.domain.services.task.TaskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.asyncFuture

object Validators {

    val Id: (String) -> Future<Either<TaskError, String>> =
            validate<String>(with = { idValidation(it) }).orFail(with = TaskError.IdNotValid)

    val Title: (String) -> Future<Either<TaskError, String>> =
            validate<String>(with = { titleValidation(it) }).orFail(with = TaskError.TitleNotValid)

    val Description: (String) -> Future<Either<TaskError, String>> =
            validate<String>(with = { descriptionValidation(it) }).orFail(with = TaskError.DescriptionNotValid)

}

private fun idValidation(it: String) = !it.isEmpty()

private fun titleValidation(it: String) = !it.isEmpty() && it.length <= 20 && it.length >= 2

private fun descriptionValidation(it: String) = !it.isEmpty() && it.length <= 200 && it.length >= 10

fun <R> validate(with: (R) -> Boolean): (R) -> R? = { it.takeIf(with) }

infix fun <L, R> ((R) -> R?).orFail(with: L): (R) -> Future<Either<L, R>> = { result: R ->
    Future.asyncFuture {
        this(result)?.let { Either.Right(it) } ?: Either.Left(with)
    }
}

