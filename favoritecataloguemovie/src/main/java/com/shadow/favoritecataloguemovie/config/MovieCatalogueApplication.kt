package com.shadow.favoritecataloguemovie.config

import android.app.Application
import android.content.Context
import com.shadow.favoritecataloguemovie.data.local.db.DbHelper

class MovieCatalogueApplication : Application() {

    private lateinit var objectGraph: AppGraph

    override fun onCreate() {
        super.onCreate()

        //Dagger
        objectGraph = Injector.create(this)
        objectGraph.inject(this)

    }

    fun getInjector(): AppGraph {
        return objectGraph
    }

    companion object {

        fun get(context: Context): MovieCatalogueApplication {
            return context.applicationContext as MovieCatalogueApplication
        }

    }

}