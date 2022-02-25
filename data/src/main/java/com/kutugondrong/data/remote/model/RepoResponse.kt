package com.kutugondrong.data.remote.model

import com.google.gson.annotations.SerializedName
import com.kutugondrong.data.model.Repo

data class RepoResponse(
    @SerializedName("id")
    val id: String,
    @SerializedName("full_name")
    val title: String?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("watchers_count")
    val star: Int?,
    @SerializedName("updated_at")
    val updateDate: String?,
) {
    fun toRepo(): Repo {
        return Repo(
            id = id,
            title = if (title.isNullOrEmpty()) {"No Title"} else {title},
            description = if (description.isNullOrEmpty()) {"No Description"} else {description},
            updateDate = if (updateDate.isNullOrEmpty()) {""} else {updateDate},
            star = star ?: 0,
        )
    }
}