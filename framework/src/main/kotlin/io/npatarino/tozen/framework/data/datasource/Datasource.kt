package io.npatarino.tozen.framework.data.datasource

import io.npatarino.tozen.framework.data.datasource.errors.DiskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future

interface Datasource<Error, Item : Any> {

    fun all(): Future<List<Either<Error, Item>>>

    fun save(item: Item): Future<Either<Error, Item>>

    fun delete(fileName: String): String

    operator fun get(fileName: String): Either<DiskError, Item>
}