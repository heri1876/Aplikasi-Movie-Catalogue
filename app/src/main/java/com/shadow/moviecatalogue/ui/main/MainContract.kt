package com.shadow.moviecatalogue.ui.main

import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.TvResponse
import com.shadow.moviecatalogue.ui.BaseContract

interface MainContract : BaseContract {

    fun getListCatalogue()

    fun setListCatalogueMovie(response: MovieResponse)

    fun setListCatalogueTv(response: TvResponse)

    fun setMessageView(EmptyMessage: Int, ProgressBar: Int, ErrorNetwork: Int)

}