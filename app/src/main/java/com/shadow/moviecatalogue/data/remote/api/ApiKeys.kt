package com.shadow.moviecatalogue.data.remote.api

import com.shadow.moviecatalogue.BuildConfig

object ApiKeys {

    const val BASE_URL = "https://api.themoviedb.org/3/"
    const val API_KEY = BuildConfig.TMDB_API_KEY
    const val LANGUAGE = "&language=en-US"

    const val MOVIE_POPULAR = "movie/popular?api_key="+ API_KEY + LANGUAGE
    const val MOVIE_SEARCH = "search/movie?api_key="+ API_KEY + LANGUAGE
    const val DAILY_MOVIE = "discover/movie?api_key="+ API_KEY
    const val TV_POPULAR = "tv/popular?api_key="+ API_KEY + LANGUAGE
    const val TV_SEARCH = "search/tv?api_key="+ API_KEY + LANGUAGE

    const val IMAGE_LOW = "https://image.tmdb.org/t/p/w185"
    const val IMAGE_MEDIUM = "https://image.tmdb.org/t/p/w300"
    const val IMAGE_HIGH = "https://image.tmdb.org/t/p/w500"

}