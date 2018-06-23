package io.npatarino.tozen.design.biology.atoms

import android.annotation.TargetApi
import android.content.Context
import android.os.Build
import android.util.AttributeSet
import android.widget.TextView
import io.npatarino.tozen.R
import io.npatarino.tozen.design.extensions.padding
import io.npatarino.tozen.design.extensions.textColor
import io.npatarino.tozen.design.extensions.textSize


class Text : TextView {

    @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
        : super(context, attrs, defStyleAttr)

    @TargetApi(Build.VERSION_CODES.LOLLIPOP) constructor(context: Context,
                                                         attrs: AttributeSet?,
                                                         defStyleAttr: Int,
                                                         defStyleRes: Int)
        : super(context, attrs, defStyleAttr, defStyleRes)

    init {
        textColor(R.color.text)
        textSize(R.dimen.textSize)
        padding(R.dimen.textPadding)
    }

}
