package com.promobileapp.chiasenhac.model

import android.os.Parcel
import android.os.Parcelable

data class Album(
    val title: String?,
    val artist: String?,
    val thumb: String?,
    val quality: String?,
    val urlAlbum: String?
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(p0: Parcel?, p1: Int) {
        p0?.writeString(title)
        p0?.writeString(artist)
        p0?.writeString(thumb)
        p0?.writeString(quality)
        p0?.writeString(urlAlbum)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Album> {
        override fun createFromParcel(parcel: Parcel): Album {
            return Album(parcel)
        }

        override fun newArray(size: Int): Array<Album?> {
            return arrayOfNulls(size)
        }
    }
}