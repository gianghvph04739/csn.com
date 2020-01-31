package com.promobileapp.chiasenhac.model

import android.os.Parcel
import android.os.Parcelable

data class Quality(var name: String?, var url: String?, var size: String?) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(), parcel.readString(), parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(url)
        parcel.writeString(size)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Quality> {
        override fun createFromParcel(parcel: Parcel): Quality {
            return Quality(parcel)
        }

        override fun newArray(size: Int): Array<Quality?> {
            return arrayOfNulls(size)
        }
    }

}