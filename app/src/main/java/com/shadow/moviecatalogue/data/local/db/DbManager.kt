package com.shadow.moviecatalogue.data.local.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.shadow.moviecatalogue.data.local.db.DbKeys.MovieColoumn.Companion.ID
import com.shadow.moviecatalogue.data.local.db.DbKeys.TABLE_MOVIE
import com.shadow.moviecatalogue.data.local.db.DbKeys.TABLE_TV


class DbManager(context: Context) {

    private val dbHelper = DbHelper(context)
    private lateinit var db : SQLiteDatabase

    fun open() {
        db = dbHelper.writableDatabase
    }

    fun insertFavoriteMovie(values: ContentValues): Long{
        return db.insert(TABLE_MOVIE, null, values)
    }

    fun insertFavoriteTv(values: ContentValues): Long{
        return db.insert(TABLE_TV, null, values)
    }

    fun deleteFavoriteMovie(ID: String): Int {
        return db.delete(TABLE_MOVIE, "${DbKeys.MovieColoumn.ID}=${ID}", null)
    }

    fun deleteFavoriteTv(ID: String): Int {
        return db.delete(TABLE_TV, "${DbKeys.TvColoumn.ID}=${ID}", null)
    }

    fun queryIdMovie(id: String?): Cursor{
        return db.query(TABLE_MOVIE, null,
            "${ID}=?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun queryAllMovie(): Cursor {
        return db.query(
            TABLE_MOVIE,
            null,
            null,
            null,
            null,
            null,
            "$ID DESC",
            null)
    }

    fun queryIdTv(id: String?): Cursor{
        return db.query(DbKeys.TABLE_TV, null,
            "${DbKeys.TvColoumn.ID}=?",
            arrayOf(id),
            null,
            null,
            null,
            null)
    }

    fun queryAllTv(): Cursor {
        return db.query(
            TABLE_TV,
            null,
            null,
            null,
            null,
            null,
            "$ID DESC",
            null)
    }

}