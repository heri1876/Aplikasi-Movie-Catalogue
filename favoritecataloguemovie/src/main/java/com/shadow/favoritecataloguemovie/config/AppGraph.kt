package com.shadow.favoritecataloguemovie.config

import com.shadow.favoritecataloguemovie.ui.main.tab.MovieFavorite
import com.shadow.favoritecataloguemovie.ui.main.tab.TvFavorite

interface AppGraph {

    fun inject(movieCatalogueApplication: MovieCatalogueApplication)

    fun inject(movieFavorite: MovieFavorite)

    fun inject(tvFavorite: TvFavorite)

}