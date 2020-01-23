package com.shadow.moviecatalogue.presenter.favorite


import android.content.Context
import android.database.Cursor
import com.shadow.moviecatalogue.data.local.db.DbKeys.MovieColoumn.Companion.CONTENT_MOVIE
import com.shadow.moviecatalogue.data.local.db.DbKeys.TvColoumn.Companion.CONTENT_TV
import com.shadow.moviecatalogue.ui.main.fragment.favorite.FavoriteContract
import com.shadow.moviecatalogue.util.Utilities
import java.util.*
import javax.inject.Inject

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

        val cursor = Objects.requireNonNull(context).contentResolver.query(
            CONTENT_TV,
            null,
            null,
            null,
            null
        ) as Cursor

        view.setListCatalogueTv(Utilities.tvCursorToArrayList(cursor))
    }

}