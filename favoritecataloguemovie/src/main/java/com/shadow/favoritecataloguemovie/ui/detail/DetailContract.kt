package com.shadow.favoritecataloguemovie.ui.detail

import android.widget.ImageView
import com.shadow.favoritecataloguemovie.ui.BaseContract

interface DetailContract : BaseContract {

    fun onErrorNetwork(view: ImageView)

    fun setImage(url: String, view: ImageView)

    fun addToFavorite()

    fun removeFromFavorite()

    fun checkItem(): Boolean
}