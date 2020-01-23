package com.shadow.moviecatalogue.ui

import android.os.Bundle

interface BaseContract {

    fun initComponent(savedInstanceState: Bundle?)

    fun onShowToast(message: String)

}