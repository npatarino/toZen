package io.npatarino.tozen.ui.home

import android.content.Context
import io.npatarino.tozen.R

sealed class HomePage(val layoutResId: Int, val title: String) {

    class ToDo(context: Context) : HomePage(R.layout.home_pager_todo, context.getString(R.string.title_todo))
    class Documents(context: Context) : HomePage(R.layout.home_pager_documents, context.getString(R.string.title_todo))
    class Notes(context: Context) : HomePage(R.layout.home_pager_notes, context.getString(R.string.title_todo))

}