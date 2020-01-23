package com.shadow.moviecatalogue.presenter.favorite

import android.content.Context
import com.shadow.moviecatalogue.ui.main.fragment.favorite.FavoriteContract

interface FavoritePresenterContract {

    fun setView(view: FavoriteContract)

    fun getFavoriteMovieList(context: Context)

    fun getFavoriteTvList(context: Context)

}