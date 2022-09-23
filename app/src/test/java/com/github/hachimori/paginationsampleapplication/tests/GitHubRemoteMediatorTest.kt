package com.github.hachimori.paginationsampleapplication.tests

import android.content.Context
import android.util.Log
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.github.hachimori.paginationsampleapplication.MainDispatcherRule
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.io.database.GitHubDatabase
import com.github.hachimori.paginationsampleapplication.io.mediator.GitHubRemoteMediator
import com.github.hachimori.paginationsampleapplication.io.network.GitHubApiService
import com.github.hachimori.paginationsampleapplication.io.network.responsemodels.UserResponse
import com.github.hachimori.paginationsampleapplication.setupEmptyMock
import com.github.hachimori.paginationsampleapplication.setupMock
import com.github.hachimori.paginationsampleapplication.utils.GITHUB_API_USER_PER_PAGE
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock
import org.robolectric.RobolectricTestRunner
import timber.log.Timber

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(RobolectricTestRunner::class)
class GitHubRemoteMediatorTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    lateinit var fakeGitHubDatabase: GitHubDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        fakeGitHubDatabase = Room.inMemoryDatabaseBuilder(context, GitHubDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun testLoadSuccess() = runTest {
        val fakeGitHubApiService: GitHubApiService = mock(GitHubApiService::class.java)
        fakeGitHubApiService.setupMock()

        val gitHubRemoteMediator = GitHubRemoteMediator(fakeGitHubApiService, fakeGitHubDatabase)

        val pagingState = PagingState<Int, User>(
            listOf(),
            null,
            PagingConfig(GITHUB_API_USER_PER_PAGE),
            GITHUB_API_USER_PER_PAGE
        )

        val mediatorResult: RemoteMediator.MediatorResult = gitHubRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)

        val loadResult = fakeGitHubDatabase.userDao().getAllUserFlow().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = GITHUB_API_USER_PER_PAGE,
                placeholdersEnabled = false
            )
        )

        assertEquals(
            (1..30).map { User(it, "login_$it", "avatarUrl_$it") },
            (loadResult as PagingSource.LoadResult.Page).data
        )
    }

    @OptIn(ExperimentalPagingApi::class)
    @Test
    fun testLoadEndOfPagination() = runTest {
        val fakeGitHubApiService: GitHubApiService = mock(GitHubApiService::class.java)
        fakeGitHubApiService.setupEmptyMock()

        val gitHubRemoteMediator = GitHubRemoteMediator(fakeGitHubApiService, fakeGitHubDatabase)

        val pagingState = PagingState<Int, User>(
            listOf(),
            null,
            PagingConfig(GITHUB_API_USER_PER_PAGE),
            GITHUB_API_USER_PER_PAGE
        )

        val mediatorResult: RemoteMediator.MediatorResult = gitHubRemoteMediator.load(LoadType.REFRESH, pagingState)
        assertTrue(mediatorResult is RemoteMediator.MediatorResult.Success)
        assertTrue((mediatorResult as RemoteMediator.MediatorResult.Success).endOfPaginationReached)

        val loadResult = fakeGitHubDatabase.userDao().getAllUserFlow().load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = GITHUB_API_USER_PER_PAGE,
                placeholdersEnabled = false
            )
        )

        assertEquals(
            emptyList<User>(),
            (loadResult as PagingSource.LoadResult.Page).data
        )
    }
}