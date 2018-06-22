package io.npatarino.tozen.data.datasource.net

import io.npatarino.tozen.domain.business.Task

fun Task.toMap(): Map<String, Any> = hashMapOf("title" to title)
