package com.shadow.moviecatalogue.config

import android.content.Context

object Injector {

    fun obtain(context: Context): AppGraph {
        return MovieCatalogueApplication.get(context).getInjector()
    }

    @Suppress("DEPRECATION")
    internal fun create(app: MovieCatalogueApplication): AppGraph {
        return DaggerAppComponent.builder().appModule(AppModule(app)).build()
    }
}