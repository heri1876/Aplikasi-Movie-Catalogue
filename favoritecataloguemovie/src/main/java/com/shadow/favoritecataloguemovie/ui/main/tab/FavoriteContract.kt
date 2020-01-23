package com.shadow.favoritecataloguemovie.ui.main.tab

import com.shadow.favoritecataloguemovie.data.model.response.ItemMovie
import com.shadow.favoritecataloguemovie.data.model.response.ItemTv
import com.shadow.favoritecataloguemovie.ui.BaseContract

interface FavoriteContract : BaseContract {

    fun getListCatalogue()

    fun setListCatalogueMovie(movie: ArrayList<ItemMovie>)

    fun setListCatalogueTv(tv: ArrayList<ItemTv>)

    fun clearList()
}