package io.npatarino.tozen.framework.domain.business

import java.util.*

val generateId: () -> String = { UUID.randomUUID().toString() }