package com.github.hachimori.paginationsampleapplication.repositories

import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.io.database.GitHubDatabase
import com.github.hachimori.paginationsampleapplication.io.mediator.GitHubRemoteMediator
import com.github.hachimori.paginationsampleapplication.io.network.GitHubApiService
import com.github.hachimori.paginationsampleapplication.utils.GITHUB_API_USER_PER_PAGE
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitHubRepository @Inject constructor(
    private val gitHubApiService: GitHubApiService,
    private val gitHubDatabase: GitHubDatabase
) {

    fun getUserPagingDataFlow(): Flow<PagingData<User>> {
        Timber.i("GitHubRepository new query")

        val pagingSourceFactory = { gitHubDatabase.userDao().getAllUserFlow() }

        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(pageSize = GITHUB_API_USER_PER_PAGE, enablePlaceholders = false),
            remoteMediator = GitHubRemoteMediator(
                gitHubApiService,
                gitHubDatabase
            ),
            pagingSourceFactory = pagingSourceFactory
        ).flow
    }
}
