package com.shadow.moviecatalogue.widget

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.shadow.moviecatalogue.R
import com.shadow.moviecatalogue.data.local.db.DbKeys.MovieColoumn.Companion.CONTENT_MOVIE
import com.shadow.moviecatalogue.data.model.response.ItemMovie
import com.shadow.moviecatalogue.data.remote.api.ApiKeys

class StackRemoteViewsFactory(private val context: Context, intent: Intent) :
    RemoteViewsService.RemoteViewsFactory {
    private var cursor: Cursor? = null

    override fun onCreate() {
        cursor = context.contentResolver.query(
            CONTENT_MOVIE, null, null, null, null
        )
    }

    private fun getItem(position: Int): ItemMovie {
        if (!cursor!!.moveToPosition(position)) {
            throw IllegalStateException("Error")
        }

        return ItemMovie(cursor!!)
    }

    override fun onDataSetChanged() {
    }

    override fun onDestroy() {

    }

    override fun getCount(): Int {
        return cursor!!.count
    }

    override fun getViewAt(position: Int): RemoteViews {
        val movie = getItem(position)
        val urlImage = ApiKeys.IMAGE_LOW + movie.poster_path

        val remoteViews = RemoteViews(context.packageName, R.layout.widget_item)

        var bmp: Bitmap? = null
        try {
            bmp = Glide.with(context)
                .asBitmap()
                .load(urlImage)
                .apply(RequestOptions().fitCenter())
                .submit()
                .get()

        } catch (e: Exception) {
            e.printStackTrace()
        }

        remoteViews.setImageViewBitmap(R.id.itemPoster, bmp)

        val bundle = Bundle()
        bundle.putString(AppsWidget.EXTRA_TITLE, movie.original_title)
        val intent = Intent()
        intent.putExtras(bundle)

        remoteViews.setOnClickFillInIntent(R.id.itemPoster, intent)
        return remoteViews
    }

    override fun getLoadingView(): RemoteViews? {
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean {
        return false
    }

}