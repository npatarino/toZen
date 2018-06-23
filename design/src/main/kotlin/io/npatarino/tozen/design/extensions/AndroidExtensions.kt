package io.npatarino.tozen.design.extensions

fun isLowerThanLollipop(): Boolean = android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.LOLLIPOP
