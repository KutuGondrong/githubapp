package com.kutugondrong.data.remote.model

import com.google.gson.annotations.SerializedName
import com.kutugondrong.data.model.User

data class UserResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val fullName: String?,
    @SerializedName("login")
    val userName: String?,
    @SerializedName("bio")
    val description: String?,
    @SerializedName("location")
    val city: String?,
    @SerializedName("email")
    val email: String?,
    @SerializedName("avatar_url")
    val avatar: String?,
    @SerializedName("followers")
    val followers: Int?,
    @SerializedName("following")
    val following: Int?,
) {
    fun toUser(): User {
        return User(
            id = id,
            fullName = if (fullName.isNullOrEmpty()) {"No Name"} else {fullName},
            userName = if (userName.isNullOrEmpty()) {""} else {"$userName"},
            description = if (description.isNullOrEmpty()) {"No Bio"} else {description},
            city = if (city.isNullOrEmpty()) {"No city"} else {city},
            email = if (email.isNullOrEmpty()) {"No email"} else {email},
            avatar = if (avatar.isNullOrEmpty()) {""} else {avatar},
            followers = followers ?: 0,
            following = following ?: 0,
        )
    }
}