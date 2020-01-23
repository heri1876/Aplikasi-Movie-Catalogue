package com.shadow.moviecatalogue.config

import android.app.Application
import android.content.Context
import com.shadow.moviecatalogue.data.local.db.DbHelper

class MovieCatalogueApplication : Application() {

    private lateinit var objectGraph: AppGraph
    private lateinit var dbHelper: DbHelper

    override fun onCreate() {
        super.onCreate()

        //Dagger
        objectGraph = Injector.create(this)
        objectGraph.inject(this)

        //DbHelper
        instance = this
        dbHelper = DbHelper(this)
    }

    @Synchronized
    fun getDbHelper(): DbHelper {
        return dbHelper
    }

    fun getInjector(): AppGraph {
        return objectGraph
    }

    companion object {

        private lateinit var instance: MovieCatalogueApplication

        fun get(context: Context): MovieCatalogueApplication {
            return context.applicationContext as MovieCatalogueApplication
        }

        @Synchronized
        fun getInstance() : MovieCatalogueApplication = instance
    }

}