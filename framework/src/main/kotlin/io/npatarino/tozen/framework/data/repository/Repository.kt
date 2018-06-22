package io.npatarino.tozen.framework.data.repository

import io.npatarino.tozen.framework.data.repository.error.RepositoryError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future

interface Repository<Item> {

    fun save(item: Item): Future<Either<RepositoryError, Item>>

}