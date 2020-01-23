package com.shadow.moviecatalogue.repository.catalogue

import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.TvResponse
import io.reactivex.Single

class CatalogueRepository(private val remoteDataSource: CatalogueRemoteDataSource) : CatalogueDataSource {

    override fun getPopularMovie(page: Int): Single<MovieResponse> {
        return remoteDataSource.getPopularMovie(page)
    }

    override fun getPopularTv(page: Int): Single<TvResponse> {
        return remoteDataSource.getPopularTv(page)
    }

    override fun getSearchMovie(title: String, page: Int): Single<MovieResponse> {
        return remoteDataSource.getSearchMovie(title, page)
    }

    override fun getSearchTv(title: String, page: Int): Single<TvResponse> {
        return remoteDataSource.getSearchTv(title, page)
    }

    override fun getDailyMovie(date: String): Single<MovieResponse> {
        return remoteDataSource.getDailyMovie(date)
    }

}