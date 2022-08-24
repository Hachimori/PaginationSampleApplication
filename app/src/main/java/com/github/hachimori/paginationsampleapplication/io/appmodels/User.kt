package com.github.hachimori.paginationsampleapplication.io.appmodels

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.github.hachimori.paginationsampleapplication.utils.GITHUB_USER_TABLE

@Entity(tableName = GITHUB_USER_TABLE)
data class User(
    @PrimaryKey
    val id: Int,
    val login: String,
    val avatarUrl: String
)
