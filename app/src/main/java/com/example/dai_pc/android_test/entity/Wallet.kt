package com.example.dai_pc.android_test.entity

import android.os.Parcel
import android.os.Parcelable

data class Wallet(val name:String,val address:String, val note : String) :Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString())
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(name)
        parcel.writeString(address)
        parcel.writeString(note)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Wallet> {
        override fun createFromParcel(parcel: Parcel): Wallet {
            return Wallet(parcel)
        }

        override fun newArray(size: Int): Array<Wallet?> {
            return arrayOfNulls(size)
        }
    }


}