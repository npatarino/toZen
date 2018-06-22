package io.npatarino.tozen.framework.data.datasource.local

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import io.npatarino.tozen.framework.data.datasource.errors.DiskError
import io.npatarino.tozen.framework.domain.types.Either
import io.npatarino.tozen.framework.domain.types.left
import io.npatarino.tozen.framework.domain.types.right

class MoshiJsonConverter<Item>(clazz: Class<Item>) : JsonConverter<Item> {

    private val jsonAdapter : JsonAdapter<Item>

    init {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        jsonAdapter = moshi.adapter(clazz)
    }

    override fun serialize(item: Item, id: String): Either<DiskError.SerializationError, String> = try {
        jsonAdapter.toJson(item).right()
    } catch (exception: Exception) {
        DiskError.SerializationError(id).left()
    }

    override fun deserialize(content: String?): Either<DiskError.DeserializationError, Item> = try {
        content?.let {
            jsonAdapter.fromJson(content)?.right() ?: DiskError.DeserializationError(content).left()
        } ?: DiskError.DeserializationError("No content").left()
    } catch (exception: Exception) {
        DiskError.DeserializationError(content!!).left()
    }

}