package io.npatarino.tozen.data.datasource.net.errors

import io.npatarino.tozen.framework.data.repository.error.RepositoryError

fun NetError.toRepositoryError() = when (this) {
    is NetError.Unknown -> RepositoryError.Unknown
}
