package com.shadow.moviecatalogue.data.model.response

import android.database.Cursor
import android.os.Parcel
import android.os.Parcelable
import com.shadow.moviecatalogue.data.local.db.DbKeys
import java.io.Serializable

data class MovieResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: ArrayList<ItemMovie>
): Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        TODO("results")
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(page)
        parcel.writeInt(total_results)
        parcel.writeInt(total_pages)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<MovieResponse> {
        override fun createFromParcel(parcel: Parcel): MovieResponse {
            return MovieResponse(parcel)
        }

        override fun newArray(size: Int): Array<MovieResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class ItemMovie(
    val id: Int,
    val original_title: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val vote_count: Int,
    val original_language: String?,
    val release_date: String?
): Parcelable {

    constructor(item: Cursor): this(
        id = item.getInt(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.ID)),
        original_title = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.TITLE)),
        overview = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.DESCRIPTION)),
        poster_path = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.POSTER)),
        backdrop_path = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.BACKDROP)),
        vote_average = item.getDouble(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.VOTE_AVERAGE)),
        vote_count = item.getInt(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.VOTE_COUNT)),
        original_language = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.ORIGINAL_LANGUAGE)),
        release_date = item.getString(item.getColumnIndexOrThrow(DbKeys.MovieColoumn.RELEASE_DATE))
    )

    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readDouble(),
        parcel.readInt(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(original_title)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
        parcel.writeString(backdrop_path)
        parcel.writeDouble(vote_average)
        parcel.writeInt(vote_count)
        parcel.writeString(original_language)
        parcel.writeString(release_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemMovie> {
        override fun createFromParcel(parcel: Parcel): ItemMovie {
            return ItemMovie(parcel)
        }

        override fun newArray(size: Int): Array<ItemMovie?> {
            return arrayOfNulls(size)
        }
    }
}