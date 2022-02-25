package com.kutugondrong.data.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class User(
    val id: String,
    val fullName: String,
    val userName: String,
    val description: String,
    val city: String,
    val email: String,
    val avatar: String,
    val followers: Int,
    val following: Int,
) : Parcelable {
    fun userNameWithLabel() : String{
        return "@$userName"
    }
}