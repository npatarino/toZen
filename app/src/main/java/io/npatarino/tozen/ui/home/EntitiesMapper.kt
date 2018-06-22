package io.npatarino.tozen.ui.home

import io.npatarino.tozen.domain.business.Task

fun Task.toMap(): Map<String, Any> = hashMapOf("title" to title)
