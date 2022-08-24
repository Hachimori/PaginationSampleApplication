package com.github.hachimori.paginationsampleapplication.io.network.responsemodels

import androidx.annotation.Keep
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.google.gson.annotations.SerializedName

@Keep
data class UserResponse(
    @SerializedName("login")
    val login: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl: String
) {
    fun mapToUser() =
        User(
            login = login,
            id = id,
            avatarUrl = avatarUrl
        )
}
