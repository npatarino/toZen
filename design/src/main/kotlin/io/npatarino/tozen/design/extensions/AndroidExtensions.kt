package io.npatarino.tozen.design.extensions

import io.npatarino.tozen.framework.domain.types.Future
import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import kotlinx.coroutines.experimental.launch

fun isLowerThanLollipop(): Boolean = android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP

fun <A> Future.Companion.uiFuture(getValue: () -> A): Future<A> = Future(async(UI) {
    getValue()
})