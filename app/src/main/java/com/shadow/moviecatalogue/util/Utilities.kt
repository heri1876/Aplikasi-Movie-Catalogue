@file:Suppress("DEPRECATION")

package com.shadow.moviecatalogue.util

import android.content.Context
import android.database.Cursor
import android.net.ConnectivityManager
import com.shadow.moviecatalogue.data.local.db.DbKeys
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.model.response.ItemTv
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

object Utilities {

    fun isNetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    fun getCurrentDate(): String {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = Date()
        val now = dateFormat.format(date)
        return now
    }

    fun movieCursorToArrayList(item: Cursor): ArrayList<ItemMovie> {
        val movieList = ArrayList<ItemMovie>()

        while (item.moveToNext()) {
            val id = item.getInt(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.ID))
            val title = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.TITLE))
            val overview = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.DESCRIPTION))
            val poster = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.POSTER))
            val backdrop = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.BACKDROP))
            val voteAverage = item.getDouble(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.VOTE_AVERAGE))
            val voteCount = item.getInt(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.VOTE_COUNT))
            val languange = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.ORIGINAL_LANGUAGE))
            val releaseDate = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.RELEASE_DATE))

            movieList.add(ItemMovie(id, title, overview, poster, backdrop, voteAverage, voteCount, languange, releaseDate))
        }
        return movieList
    }

    fun tvCursorToArrayList(item: Cursor): ArrayList<ItemTv> {
        val movieList = ArrayList<ItemTv>()

        while (item.moveToNext()) {
            val id = item.getInt(item.getColumnIndexOrThrow(DbKeys.TvColoumn.ID))
            val title = item.getString(item.getColumnIndexOrThrow(DbKeys.TvColoumn.TITLE))
            val overview = item.getString(item.getColumnIndexOrThrow(DbKeys.TvColoumn.DESCRIPTION))
            val poster = item.getString(item.getColumnIndexOrThrow(DbKeys.TvColoumn.POSTER))
            val backdrop = item.getString(item.getColumnIndexOrThrow(DbKeys.TvColoumn.BACKDROP))
            val voteAverage = item.getDouble(item.getColumnIndexOrThrow(DbKeys.TvColoumn.VOTE_AVERAGE))
            val voteCount = item.getInt(item.getColumnIndexOrThrow(DbKeys.TvColoumn.VOTE_COUNT))
            val languange = item.getString(item.getColumnIndexOrThrow(DbKeys.TvColoumn.ORIGINAL_LANGUAGE))
            val firsAirDate = item.getString(item.getColumnIndexOrThrow(DbKeys.TvColoumn.FIRST_AIR_DATE))

            movieList.add(ItemTv(id, title, overview, poster, backdrop, voteAverage, voteCount, languange, firsAirDate))
        }
        return movieList
    }
}