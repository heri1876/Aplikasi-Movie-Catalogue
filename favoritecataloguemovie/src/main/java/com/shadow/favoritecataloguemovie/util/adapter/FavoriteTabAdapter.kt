package com.shadow.favoritecataloguemovie.util.adapter

import android.content.Context
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.shadow.favoritecataloguemovie.R
import com.shadow.favoritecataloguemovie.ui.main.tab.MovieFavorite
import com.shadow.favoritecataloguemovie.ui.main.tab.TvFavorite

class FavoriteTabAdapter internal constructor(private val mContext: Context, fm: FragmentManager) :
    FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    @StringRes
    private val TAB_TITLES = intArrayOf(R.string.movie_catalogue, R.string.tv_catalogue)

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = MovieFavorite()
            1 -> fragment = TvFavorite()
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return mContext.resources.getString(TAB_TITLES[position])
    }

    override fun getCount(): Int {
        return 2
    }
}