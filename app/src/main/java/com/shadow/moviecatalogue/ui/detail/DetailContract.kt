package com.shadow.moviecatalogue.ui.detail

import android.widget.ImageView
import com.shadow.moviecatalogue.ui.BaseContract

interface DetailContract : BaseContract {

    fun onErrorNetwork(view: ImageView)

    fun setImage(url: String, view: ImageView)

    fun addToFavorite()

    fun removeFromFavorite()

    fun checkItem(): Boolean
}