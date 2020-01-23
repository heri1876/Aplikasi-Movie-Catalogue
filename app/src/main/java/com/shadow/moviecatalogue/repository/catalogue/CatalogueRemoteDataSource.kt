package com.shadow.moviecatalogue.repository.catalogue

import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.TvResponse
import com.shadow.moviecatalogue.data.remote.service.CatalogueService
import com.shadow.moviecatalogue.repository.scheduler.BaseSchedulerProvider
import io.reactivex.Single
import javax.inject.Inject

class CatalogueRemoteDataSource @Inject
constructor(private val catalogueService: CatalogueService,
            private val schedulerProvider: BaseSchedulerProvider) : CatalogueDataSource {

    override fun getPopularMovie(page : Int): Single<MovieResponse> {
        return catalogueService.getPopularMovie(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getPopularTv(page: Int): Single<TvResponse> {
        return catalogueService.getPopularTv(page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getSearchMovie(title: String, page: Int): Single<MovieResponse> {
        return catalogueService.getSearchMovie(title, page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getSearchTv(title: String, page: Int): Single<TvResponse> {
        return catalogueService.getSearchTv(title, page)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }

    override fun getDailyMovie(date: String): Single<MovieResponse> {
        return catalogueService.getDailyMovie(date, date)
            .subscribeOn(schedulerProvider.io())
            .observeOn(schedulerProvider.ui())
    }
}