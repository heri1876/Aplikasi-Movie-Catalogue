package com.shadow.moviecatalogue.config

import com.shadow.moviecatalogue.data.remote.api.DataModule
import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class, DataModule::class]
)

@Singleton
interface AppComponent : AppGraph