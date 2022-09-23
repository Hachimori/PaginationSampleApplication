package com.github.hachimori.paginationsampleapplication.io.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.io.database.GitHubDatabase
import com.github.hachimori.paginationsampleapplication.io.network.GitHubApiService
import com.github.hachimori.paginationsampleapplication.io.network.getBodyOrThrow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@OptIn(ExperimentalPagingApi::class)
@Singleton
class GitHubRemoteMediator @Inject constructor(
    private val gitHubApiService: GitHubApiService,
    private val gitHubDatabase: GitHubDatabase
) : RemoteMediator<Int, User>() {

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(loadType: LoadType, state: PagingState<Int, User>): MediatorResult {
        val since = when (loadType) {
            // When it does Refresh, it always start a new search, starting with since = 1.
            LoadType.REFRESH -> 1

            // In this app, it never prepend items because Refresh always load the first page.
            // So, it should return successful result with end of pagination.
            LoadType.PREPEND ->
                return MediatorResult.Success(endOfPaginationReached = true)

            // Request user list whose ID is larger than last user's ID.
            // If is NULL, return successful result with end of pagination
            // because there's no item to load.
            LoadType.APPEND -> {
                val lastItem = state.lastItemOrNull()
                    ?: return MediatorResult.Success(endOfPaginationReached = true)
                lastItem.id
            }
        }

        return try {
            val userList = gitHubApiService.getUserList(since).getBodyOrThrow()

            gitHubDatabase.withTransaction {
                val userDao = gitHubDatabase.userDao()
                if (loadType == LoadType.REFRESH) {
                    userDao.clearAllUser()
                }
                userDao.insertUserList(userList.map { it.mapToUser() })
            }

            MediatorResult.Success(
                endOfPaginationReached = userList.isEmpty()
            )
        } catch (e: Exception) {
            MediatorResult.Error(e)
        }
    }
}
