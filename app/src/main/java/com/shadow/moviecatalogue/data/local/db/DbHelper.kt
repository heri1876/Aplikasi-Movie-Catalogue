package com.shadow.moviecatalogue.data.local.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DbHelper(context: Context) : SQLiteOpenHelper(
    context,
    DB_NAME, null,
    DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_TABLE_MOVIE)
        db.execSQL(SQL_CREATE_TABLE_TV)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + DbKeys.TABLE_MOVIE)
        db.execSQL("DROP TABLE IF EXISTS " + DbKeys.TABLE_TV)

        onCreate(db)
    }

    companion object {

        var DB_NAME = "dbmovietv"

        private val DB_VERSION = 1

        private val SQL_CREATE_TABLE_MOVIE = String.format(
            ("CREATE TABLE %s"
                    + "(%s INTEGER NOT NULL UNIQUE," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT," +
                    "%s DOUBLE NOT NULL," +
                    "%s INTEGER NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)"),
            DbKeys.TABLE_MOVIE,
            DbKeys.MovieColoumn.ID,
            DbKeys.MovieColoumn.TITLE,
            DbKeys.MovieColoumn.DESCRIPTION,
            DbKeys.MovieColoumn.POSTER,
            DbKeys.MovieColoumn.BACKDROP,
            DbKeys.MovieColoumn.VOTE_AVERAGE,
            DbKeys.MovieColoumn.VOTE_COUNT,
            DbKeys.MovieColoumn.ORIGINAL_LANGUAGE,
            DbKeys.MovieColoumn.RELEASE_DATE
        )

        private val SQL_CREATE_TABLE_TV = String.format(
            ("CREATE TABLE %s"
                    + "(%s INTEGER NOT NULL UNIQUE," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT," +
                    "%s DOUBLE NOT NULL," +
                    "%s INTEGER NOT NULL," +
                    "%s TEXT NOT NULL," +
                    "%s TEXT NOT NULL)"),
            DbKeys.TABLE_TV,
            DbKeys.TvColoumn.ID,
            DbKeys.TvColoumn.TITLE,
            DbKeys.TvColoumn.DESCRIPTION,
            DbKeys.TvColoumn.POSTER,
            DbKeys.TvColoumn.BACKDROP,
            DbKeys.TvColoumn.VOTE_AVERAGE,
            DbKeys.TvColoumn.VOTE_COUNT,
            DbKeys.TvColoumn.ORIGINAL_LANGUAGE,
            DbKeys.TvColoumn.FIRST_AIR_DATE
        )
    }
}
