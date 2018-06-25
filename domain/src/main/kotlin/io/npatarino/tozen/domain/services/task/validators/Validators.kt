package io.npatarino.tozen.domain.services.task.validators

import io.npatarino.tozen.domain.services.task.TaskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.asyncFuture

object Validators {

    val Title: (String) -> Future<Either<TaskError, String>> =
            validate<String>(with = { titleValidation(it) }).orElseFail(with = TaskError.TitleNotValid)

}

private fun titleValidation(it: String) = !it.isEmpty() && it.length <= 20

fun <R> validate(with: (R) -> Boolean): (R) -> R? = { it.takeIf(with) }

infix fun <L, R> ((R) -> R?).orElseFail(with: L): (R) -> Future<Either<L, R>> = { result: R ->
    Future.asyncFuture {
        this(result)?.let { Either.Right(it) } ?: Either.Left(with)
    }
}

