package io.npatarino.tozen.framework.data.errors

sealed class DiskError {

    class NoSuchFile(val fileName: String) : DiskError()
    class SerializationError(val fileName: String) : DiskError()
    class DeserializationError(val content: String) : DiskError()

}