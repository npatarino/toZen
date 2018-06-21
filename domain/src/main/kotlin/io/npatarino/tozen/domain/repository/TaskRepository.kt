package io.npatarino.tozen.domain.repository

import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.domain.repository.error.RepositoryError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future

interface TaskRepository {

    fun save(task: Task): Future<Either<RepositoryError, Task>>

}