package io.npatarino.tozen.framework.data.datasource.local

import java.io.File

class ReadableFolder(private val root: File) {

    fun createIfNeeded() {
        if (!root.exists()) {
            root.mkdirs()
        }
    }

    fun files(): Collection<File> = root.listFiles().toList()

    operator fun get(id: String): File = File(root, id)

    fun save(name: String, content: String) = File(root, name).bufferedWriter().use { it.write(content) }

    fun delete(fileName: String): String {
        val file = File(root, fileName)
        if (file.exists()) {
            file.delete()
        }
        return fileName
    }

}

