package com.shadow.moviecatalogue.ui.main.fragment.favorite

import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.model.response.ItemTv
import com.shadow.moviecatalogue.ui.BaseContract

interface FavoriteContract : BaseContract {

    fun getListCatalogue()

    fun setListCatalogueMovie(movie: ArrayList<ItemMovie>)

    fun setListCatalogueTv(tv: ArrayList<ItemTv>)

    fun clearList()

    fun setMessageView(EmptyMessage: Int, ProgressBar: Int, ErrorNetwork: Int)
}