package com.github.hachimori.paginationsampleapplication

import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.ui.userlist.UserListViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import org.mockito.Mockito.`when`

/**
 * This mock returns;
 *    User(id=0..100, login="login_0..100", avatarUrl="avatarUrl_00..100")
 */
fun UserListViewModel.setupMock() {
    val mockedFlow: Flow<PagingData<User>> =
        flowOf(
            PagingData.from(
                data = (1..100).map {
                    User(it, "login_$it", "avatarUrl_$it")
                },
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.NotLoading(true)
                )
            )
        )

    `when`(pagingDataFlow).thenReturn(mockedFlow)
}

/**
 * This mock simulates loading error.
 */
fun UserListViewModel.setupErrorMock() {
    val mockedFlow: Flow<PagingData<User>> =
        flowOf(
            PagingData.from(
                data = emptyList(),
                sourceLoadStates = LoadStates(
                    refresh = LoadState.NotLoading(true),
                    prepend = LoadState.NotLoading(true),
                    append = LoadState.Error(Exception())
                )
            )
        )

    `when`(pagingDataFlow).thenReturn(mockedFlow)
}
