package io.npatarino.tozen.design.extensions

import android.content.Context
import android.support.annotation.ColorRes
import android.support.annotation.DimenRes
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

fun Context.getCompatColor(@ColorRes colorInt: Int): Int = ContextCompat.getColor(this, colorInt)

fun TextView.textColor(@ColorRes colorInt: Int){
    setTextColor(context.getCompatColor(colorInt))
}

fun TextView.textSize(@DimenRes size: Int){
    setTextSize(TypedValue.COMPLEX_UNIT_PX, context.resources.getDimension(size))
}

fun View.padding(@DimenRes dimen: Int) {
    val padding = context.resources.getDimension(dimen).toInt()
    setPadding(padding, padding, padding, padding)
}

fun View.paddingHorizontal(@DimenRes dimen: Int) {
    val padding = context.resources.getDimension(dimen).toInt()
    setPadding(padding, paddingTop, padding, paddingBottom)
}

fun View.paddingVertical(@DimenRes dimen: Int) {
    val padding = context.resources.getDimension(dimen).toInt()
    setPadding(paddingLeft, padding, paddingRight, padding)
}

fun ViewGroup.inflate(layoutResId: Int): View = LayoutInflater.from(context).inflate(layoutResId, this, false)
