package io.npatarino.tozen.data.repository

import io.npatarino.tozen.data.datasource.net.errors.NetError
import io.npatarino.tozen.data.datasource.net.errors.toRepositoryError
import io.npatarino.tozen.domain.business.Task
import io.npatarino.tozen.framework.data.datasource.Datasource
import io.npatarino.tozen.framework.data.datasource.errors.DiskError
import io.npatarino.tozen.framework.data.repository.Repository
import io.npatarino.tozen.framework.data.repository.error.RepositoryError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.map
import io.npatarino.tozen.framework.domain.types.right

class TaskRepository(private val netDatasource: Datasource<NetError, Task>,
                     private val diskDatasource: Datasource<DiskError, Task>) : Repository<Task> {


    override fun all(): Future<Either<RepositoryError, List<Task>>> = netDatasource.all().map {
        it.mapNotNull { it.getOrNull() }.right()
    }

    override fun save(item: Task): Future<Either<RepositoryError, Task>> = netDatasource.save(item).map {
        it.bimap({ it.toRepositoryError() }, {
            diskDatasource.save(it)
            it
        })
    }

}