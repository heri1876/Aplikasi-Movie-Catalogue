package com.shadow.moviecatalogue.data.remote.api

import com.shadow.moviecatalogue.data.remote.service.CatalogueService
import com.shadow.moviecatalogue.repository.catalogue.CatalogueDataSource
import com.shadow.moviecatalogue.repository.catalogue.CatalogueRemoteDataSource
import com.shadow.moviecatalogue.repository.catalogue.CatalogueRepository
import com.shadow.moviecatalogue.repository.scheduler.BaseSchedulerProvider
import com.shadow.moviecatalogue.repository.scheduler.SchedulerProvider
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ApiModule::class])
class DataModule {

    @Provides
    @Singleton
    internal fun provideScheduler(): BaseSchedulerProvider = SchedulerProvider.getInstance()

    @Provides
    @Singleton
    internal fun provideCatalogueRepository(remoteDataSource: CatalogueRemoteDataSource):
            CatalogueRepository = CatalogueRepository(remoteDataSource)

    @Provides
    @Singleton
    internal fun provideCatalogueRemoteDataSource(
        service: CatalogueService, schedulerProvider: BaseSchedulerProvider):
            CatalogueDataSource = CatalogueRemoteDataSource(service, schedulerProvider)

}