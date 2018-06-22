package io.npatarino.tozen.framework.data.datasource.local

import io.npatarino.tozen.framework.data.datasource.errors.DiskError
import io.npatarino.tozen.framework.domain.types.Either

interface JsonConverter<Item> {

    fun serialize(item: Item, id: String): Either<DiskError.SerializationError, String>

    fun deserialize(content: String?): Either<DiskError.DeserializationError, Item>

}