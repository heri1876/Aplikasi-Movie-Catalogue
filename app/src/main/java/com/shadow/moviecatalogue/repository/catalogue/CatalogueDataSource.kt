package com.shadow.moviecatalogue.repository.catalogue

import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.TvResponse
import io.reactivex.Single

interface CatalogueDataSource {

    fun getPopularMovie(page: Int) : Single<MovieResponse>

    fun getPopularTv(page: Int) : Single<TvResponse>

    fun getSearchMovie(title: String, page: Int) : Single<MovieResponse>

    fun getSearchTv(title: String, page: Int) : Single<TvResponse>

    fun getDailyMovie(date: String) : Single<MovieResponse>
}