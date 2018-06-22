package io.npatarino.tozen.framework.data.datasource.local

import io.npatarino.tozen.framework.data.datasource.Datasource
import io.npatarino.tozen.framework.data.datasource.errors.DiskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.Future
import io.npatarino.tozen.framework.domain.types.left
import kotlinx.coroutines.experimental.async

class DiskDatasource<Item : Any>(private val folder: ReadableFolder,
                                 private val jsonConverter: JsonConverter<Item>,
                                 private val generateId: () -> String) : Datasource<DiskError, Item> {

    init {
        folder.createIfNeeded()
    }

    override fun all(): Future<List<Either<DiskError, Item>>> = Future(async {
        folder.files().map { jsonConverter.deserialize(it.readText()) }
    })

    override fun save(item: Item): Future<Either<DiskError, Item>> = Future(async {
        val id = generateId()
        jsonConverter.serialize(item, id).map {
            folder.save(id, it)
            item
        }
    })

    override operator fun get(fileName: String): Either<DiskError, Item> {
        val file = folder[fileName]
        return if (file.exists()) {
            jsonConverter.deserialize(file.readText())
        } else {
            DiskError.NoSuchFile(fileName).left()
        }
    }

    override fun delete(fileName: String): String = folder.delete(fileName)

}