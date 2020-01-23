package com.shadow.moviecatalogue.config

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule (val application: MovieCatalogueApplication) {

    @Provides
    @Singleton
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    fun provideContext(): Context = application.baseContext

    @Provides
    @Singleton
    fun provideContentResolver() : ContentResolver = application.contentResolver

}