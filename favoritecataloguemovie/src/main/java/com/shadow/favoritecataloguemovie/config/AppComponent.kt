package com.shadow.favoritecataloguemovie.config

import dagger.Component
import javax.inject.Singleton

@Component(
    modules = [AppModule::class]
)

@Singleton
interface AppComponent : AppGraph