package com.shadow.moviecatalogue.data.local.provider

import android.content.ContentProvider
import android.content.ContentUris
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.shadow.moviecatalogue.data.local.db.DbKeys.AUTHORITY
import com.shadow.moviecatalogue.data.local.db.DbKeys.MovieColoumn.Companion.CONTENT_MOVIE
import com.shadow.moviecatalogue.data.local.db.DbKeys.TABLE_MOVIE
import com.shadow.moviecatalogue.data.local.db.DbKeys.TABLE_TV
import com.shadow.moviecatalogue.data.local.db.DbKeys.TvColoumn.Companion.CONTENT_TV
import com.shadow.moviecatalogue.data.local.db.DbManager

class CatalogueProvider : ContentProvider() {

    override fun onCreate(): Boolean {
        dbManager = DbManager(context!!)
        dbManager.open()
        return true
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {

        var added: Long = 0
        var contentUri: Uri? = null

        if (values != null) {
            when (sUriMatcher.match(uri)) {
                MOVIE -> {
                    added = dbManager.insertFavoriteMovie(values)
                    if (added > 0) {
                        contentUri = ContentUris.withAppendedId(CONTENT_MOVIE, added)
                    }
                }
                TV -> {
                    added = dbManager.insertFavoriteTv(values)
                    if (added > 0) {
                        contentUri = ContentUris.withAppendedId(CONTENT_TV, added)
                    }
                }
            }
        }

        if (added > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return contentUri

    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        var deleted = 0

        when (sUriMatcher.match(uri)) {
            MOVIE_ID -> {
                deleted = dbManager.deleteFavoriteMovie(uri.lastPathSegment!!)
            }
            TV_ID -> {
                deleted = dbManager.deleteFavoriteTv(uri.lastPathSegment!!)
            }
        }

        if (deleted > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }
        return deleted
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val cursor: Cursor?
        when (sUriMatcher.match(uri)) {
            MOVIE -> cursor = dbManager.queryAllMovie()
            MOVIE_ID -> cursor = dbManager.queryIdMovie(uri.lastPathSegment)
            TV -> cursor = dbManager.queryAllTv()
            TV_ID -> cursor = dbManager.queryIdTv(uri.lastPathSegment)
            else -> cursor = null
        }

        if (cursor != null) {
            cursor.setNotificationUri(context?.contentResolver, uri)
        }
        return cursor
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        return 0
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    companion object {
        private const val MOVIE = 101
        private const val MOVIE_ID = 102

        private const val TV = 201
        private const val TV_ID = 202

        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var dbManager: DbManager

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_MOVIE, MOVIE)

            sUriMatcher.addURI(AUTHORITY, "$TABLE_MOVIE/#", MOVIE_ID)
        }

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_TV, TV)

            sUriMatcher.addURI(AUTHORITY, "$TABLE_TV/#", TV_ID)
        }

    }

}
