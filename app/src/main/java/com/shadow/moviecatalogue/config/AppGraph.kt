package com.shadow.moviecatalogue.config

import com.shadow.moviecatalogue.ui.main.fragment.favorite.tab.MovieFavorite
import com.shadow.moviecatalogue.ui.main.fragment.favorite.tab.TvFavorite
import com.shadow.moviecatalogue.ui.main.fragment.home.tab.MovieFragment
import com.shadow.moviecatalogue.ui.main.fragment.home.tab.TvFragment
import com.shadow.moviecatalogue.ui.main.fragment.search.SearchFragment
import com.shadow.moviecatalogue.ui.setting.SettingActivity
import com.shadow.moviecatalogue.util.notification.ReleaseReceiver

interface AppGraph {

    fun inject(movieCatalogueApplication: MovieCatalogueApplication)

    fun inject(movieFragment: MovieFragment)

    fun inject(tvFragment: TvFragment)

    fun inject(searchFragment: SearchFragment)

    fun inject(movieFavorite: MovieFavorite)

    fun inject(tvFavorite: TvFavorite)

    fun inject(settingActivity: SettingActivity)

    fun inject(releaseReceiver: ReleaseReceiver)

}