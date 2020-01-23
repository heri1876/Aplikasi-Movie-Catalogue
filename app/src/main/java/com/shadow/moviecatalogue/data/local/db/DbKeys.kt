package com.shadow.moviecatalogue.data.local.db

import android.net.Uri
import android.provider.BaseColumns


object DbKeys {

    const val AUTHORITY = "com.shadow.moviecatalogue"
    const val SCHEME = "content"

    internal var TABLE_MOVIE = "MOVIE"
    internal var TABLE_TV = "TV"

    internal class MovieColoumn : BaseColumns {
        companion object {
            var ID = "id"
            var TITLE = "original_title"
            var DESCRIPTION = "overview"
            var POSTER = "poster_path"
            var BACKDROP = "backdrop_path"
            var VOTE_AVERAGE = "vote_average"
            var VOTE_COUNT = "vote_count"
            var ORIGINAL_LANGUAGE = "original_language"
            var RELEASE_DATE = "release_date"

            val CONTENT_MOVIE = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_MOVIE)
                .build()
        }
    }

    internal class TvColoumn : BaseColumns {
        companion object {
            var ID = "id"
            var TITLE = "original_name"
            var DESCRIPTION = "overview"
            var POSTER = "poster_path"
            var BACKDROP = "backdrop_path"
            var VOTE_AVERAGE = "vote_average"
            var VOTE_COUNT = "vote_count"
            var ORIGINAL_LANGUAGE = "original_language"
            var FIRST_AIR_DATE = "first_air_date"

            val CONTENT_TV = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build()
        }
    }
}