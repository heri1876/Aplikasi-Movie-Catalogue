package com.shadow.favoritecataloguemovie.presenter.favorite

import android.content.Context
import com.shadow.favoritecataloguemovie.ui.main.tab.FavoriteContract

interface FavoritePresenterContract {

    fun setView(view: FavoriteContract)

    fun getFavoriteMovieList(context: Context)

    fun getFavoriteTvList(context: Context)

}