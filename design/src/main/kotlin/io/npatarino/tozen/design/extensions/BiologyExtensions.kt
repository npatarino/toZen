package io.npatarino.tozen.design.extensions

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet

fun AttributeSet?.init(context: Context, styleable: IntArray, function: (TypedArray) -> Unit) {
    this.let {
        val typedArray = context.obtainStyledAttributes(it, styleable, 0, 0)
        function(typedArray)
        typedArray.recycle()
    }
}

fun TypedArray.assignText(styleable: Int, function: (String) -> Unit) {
    function(getText(styleable)?.toString() ?: "")
}
