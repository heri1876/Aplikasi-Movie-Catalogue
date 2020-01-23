package com.shadow.moviecatalogue.presenter.catalogue

import com.shadow.moviecatalogue.ui.main.MainContract

interface CataloguePresenterContract {

    fun setView(view: MainContract)

    fun getCatalogueMovieList(page: Int)

    fun getCatalogueTvList(page: Int)

    fun getSearchMovieList(title: String, page: Int)

    fun getSearchTvList(title: String, page: Int)
}
