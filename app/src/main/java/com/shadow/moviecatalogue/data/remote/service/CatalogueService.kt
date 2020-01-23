package com.shadow.moviecatalogue.data.remote.service

import com.shadow.moviecatalogue.data.model.response.MovieResponse
import com.shadow.moviecatalogue.data.model.response.TvResponse
import com.shadow.moviecatalogue.data.remote.api.ApiKeys
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface CatalogueService {

    @GET(ApiKeys.MOVIE_POPULAR)
    fun getPopularMovie(@Query("page") page: Int): Single<MovieResponse>

    @GET(ApiKeys.TV_POPULAR)
    fun getPopularTv(@Query("page") page: Int): Single<TvResponse>

    @GET(ApiKeys.MOVIE_SEARCH)
    fun getSearchMovie(@Query("query") title: String, @Query("page") page: Int): Single<MovieResponse>

    @GET(ApiKeys.TV_SEARCH)
    fun getSearchTv(@Query("query") title: String, @Query("page") page: Int): Single<TvResponse>

    @GET(ApiKeys.DAILY_MOVIE)
    fun getDailyMovie(@Query("primary_release_date.gte") date1: String,
                      @Query("primary_release_date.lte") date2: String): Single<MovieResponse>

}