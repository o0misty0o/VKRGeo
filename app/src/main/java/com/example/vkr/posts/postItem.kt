package com.example.vkr.posts

import java.util.*
import android.os.Parcel
import android.os.Parcelable

class PostItem() : Parcelable {
    var uuid: String = UUID.randomUUID().toString()
    var imageLink: String = ""
    var postTitle: String = ""
    var postText: String = ""
    var postCoord1: String = ""
    var postCoord2: String = ""
    var date: String = ""
    var userName: String = ""

    constructor(parcel: Parcel) : this() {
        uuid = parcel.readString() ?: ""
        imageLink = parcel.readString() ?: ""
        postTitle = parcel.readString() ?: ""
        postText = parcel.readString() ?: ""
        postCoord1 = parcel.readString() ?: ""
        postCoord2 = parcel.readString() ?: ""
        date = parcel.readString() ?: ""
        userName = parcel.readString() ?: ""
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(uuid)
        parcel.writeString(imageLink)
        parcel.writeString(postTitle)
        parcel.writeString(postText)
        parcel.writeString(postCoord1)
        parcel.writeString(postCoord2)
        parcel.writeString(date)
        parcel.writeString(userName)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<PostItem> {
        override fun createFromParcel(parcel: Parcel): PostItem {
            return PostItem(parcel)
        }

        override fun newArray(size: Int): Array<PostItem?> {
            return arrayOfNulls(size)
        }
    }
}