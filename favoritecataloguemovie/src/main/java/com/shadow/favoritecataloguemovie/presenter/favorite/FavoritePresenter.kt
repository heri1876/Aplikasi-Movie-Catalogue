package com.shadow.favoritecataloguemovie.presenter.favorite

import android.annotation.SuppressLint
import android.content.Context
import android.database.Cursor
import com.shadow.favoritecataloguemovie.data.local.db.DbKeys.MovieColoumn.Companion.CONTENT_MOVIE
import com.shadow.favoritecataloguemovie.data.local.db.DbKeys.TvColoumn.Companion.CONTENT_TV
import com.shadow.favoritecataloguemovie.ui.main.tab.FavoriteContract
import com.shadow.favoritecataloguemovie.util.Utilities
import javax.inject.Inject

@SuppressLint("Recycle")
class FavoritePresenter @Inject constructor() : FavoritePresenterContract {

    private lateinit var view: FavoriteContract

    override fun setView(view: FavoriteContract) {
        this.view = view
    }

    override fun getFavoriteMovieList(context: Context) {

        val cursor = context.contentResolver.query(
            CONTENT_MOVIE,
            null,
            null,
            null,
            null
        ) as Cursor

        view.setListCatalogueMovie(Utilities.movieCursorToArrayList(cursor))

    }

    override fun getFavoriteTvList(context: Context) {

        val cursor = context.contentResolver.query(
            CONTENT_TV,
            null,
            null,
            null,
            null
        ) as Cursor

        view.setListCatalogueTv(Utilities.tvCursorToArrayList(cursor))
    }

}