package io.npatarino.tozen.domain.business

import io.npatarino.tozen.domain.services.task.TaskError
import io.npatarino.tozen.domain.services.task.validators.Validators.Description
import io.npatarino.tozen.domain.services.task.validators.Validators.Id
import io.npatarino.tozen.domain.services.task.validators.Validators.Title
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.ap
import io.npatarino.tozen.framework.domain.types.curried
import io.npatarino.tozen.framework.domain.types.pure

data class Task internal constructor(val id: String, val title: String, val description: String)

fun createTask(id: String, title: String, description: String): Future<Either<TaskError, Task>> =
        Future.pure(Either.pure(::Task.curried())) ap
                Id(id) ap
                Title(title) ap
                Description(description)