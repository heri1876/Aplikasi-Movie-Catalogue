package com.shadow.favoritecataloguemovie.ui.main

import com.shadow.favoritecataloguemovie.data.model.response.MovieResponse
import com.shadow.favoritecataloguemovie.data.model.response.TvResponse
import com.shadow.favoritecataloguemovie.ui.BaseContract

interface MainContract : BaseContract {

    fun getListCatalogue()

    fun setListCatalogueMovie(response: MovieResponse)

    fun setListCatalogueTv(response: TvResponse)

    fun onErrorNetwork()

    fun removeLoadingBar()

    fun clearList()
}