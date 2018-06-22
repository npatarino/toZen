package io.npatarino.tozen.ui.home

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup
import android.view.LayoutInflater

class HomeViewPagerAdapter(private val context: Context) : PagerAdapter() {

    override fun instantiateItem(collection: ViewGroup, position: Int): Any {
        val page = pageFrom(position)
        val inflater = LayoutInflater.from(context)
        val layout = inflater.inflate(page.layoutResId, collection, false) as ViewGroup
        collection.addView(layout)
        return layout
    }

    override fun destroyItem(collection: ViewGroup, position: Int, view: Any) {
        collection.removeView(view as View)
    }

    override fun getCount(): Int {
        return 3 // TODO: Get the numbers of subclasses of a sealed class
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val page = pageFrom(position)
        return page.title
    }

    private fun pageFrom(position: Int): HomePage {
        return when (position) {
            1    -> HomePage.Documents(context)
            2    -> HomePage.Notes(context)
            else -> HomePage.ToDo(context)
        }
    }

}