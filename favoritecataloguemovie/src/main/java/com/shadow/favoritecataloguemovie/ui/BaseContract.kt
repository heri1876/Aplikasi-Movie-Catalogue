package com.shadow.favoritecataloguemovie.ui

import android.os.Bundle

interface BaseContract {

    fun initComponent(savedInstanceState: Bundle?)

    fun onShowToast(message: String)

}