package com.github.hachimori.paginationsampleapplication.io.database

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("SELECT * FROM github_user_table")
    fun getAllUserFlow(): PagingSource<Int, User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUserList(userList: List<User>)

    @Query("DELETE FROM github_user_table")
    suspend fun clearAllUser()
}
