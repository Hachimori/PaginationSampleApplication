package com.github.hachimori.paginationsampleapplication.io.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.github.hachimori.paginationsampleapplication.io.appmodels.User

@Database(
    entities = [ User::class ],
    version = 1
)
abstract class GitHubDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
