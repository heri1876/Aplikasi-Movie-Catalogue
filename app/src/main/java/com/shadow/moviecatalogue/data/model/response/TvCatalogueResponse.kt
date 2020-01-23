package com.shadow.moviecatalogue.data.model.response

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

data class TvResponse(
    val page: Int,
    val total_results: Int,
    val total_pages: Int,
    val results: ArrayList<ItemTv>
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

    companion object CREATOR : Parcelable.Creator<TvResponse> {
        override fun createFromParcel(parcel: Parcel): TvResponse {
            return TvResponse(parcel)
        }

        override fun newArray(size: Int): Array<TvResponse?> {
            return arrayOfNulls(size)
        }
    }
}

data class ItemTv(
    val id: Int,
    val original_name: String?,
    val overview: String?,
    val poster_path: String?,
    val backdrop_path: String?,
    val vote_average: Double,
    val vote_count: Int,
    val original_language: String?,
    val first_air_date: String?
): Parcelable {
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
        parcel.writeString(original_name)
        parcel.writeString(overview)
        parcel.writeString(poster_path)
        parcel.writeString(backdrop_path)
        parcel.writeDouble(vote_average)
        parcel.writeInt(vote_count)
        parcel.writeString(original_language)
        parcel.writeString(first_air_date)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemTv> {
        override fun createFromParcel(parcel: Parcel): ItemTv {
            return ItemTv(parcel)
        }

        override fun newArray(size: Int): Array<ItemTv?> {
            return arrayOfNulls(size)
        }
    }
}