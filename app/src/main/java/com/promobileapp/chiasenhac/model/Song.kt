package com.promobileapp.chiasenhac.model

import android.os.Parcel
import android.os.Parcelable

data class Song(
        var title: String?,
        var artist: String?,
        var quality: String?,
        var thumb: String?,
        var urlSong: String?
):Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString(), parcel.readString()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(title)
        parcel.writeString(artist)
        parcel.writeString(quality)
        parcel.writeString(thumb)
        parcel.writeString(urlSong)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Song> {
        override fun createFromParcel(parcel: Parcel): Song {
            return Song(parcel)
        }

        override fun newArray(size: Int): Array<Song?> {
            return arrayOfNulls(size)
        }
    }
}