package com.github.hachimori.paginationsampleapplication

import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.io.network.GitHubApiService
import com.github.hachimori.paginationsampleapplication.io.network.responsemodels.UserResponse
import com.github.hachimori.paginationsampleapplication.utils.GITHUB_API_USER_PER_PAGE
import kotlinx.coroutines.flow.flow
import org.mockito.Mockito.`when`
import retrofit2.Response


suspend fun GitHubApiService.setupMock() {
    var since = 1
    while (since < 100) {
        val userList = (since until since + GITHUB_API_USER_PER_PAGE)
            .map { UserResponse("login_$it", it, "avatarUrl_$it") }

        `when`(getUserList(since, GITHUB_API_USER_PER_PAGE)).thenReturn(
            Response.success(userList)
        )

        since = userList.last().id
    }

    `when`(getUserList(1000, GITHUB_API_USER_PER_PAGE)).thenReturn(
        Response.success(emptyList())
    )
}

suspend fun GitHubApiService.setupEmptyMock() {
    `when`(getUserList(1, GITHUB_API_USER_PER_PAGE)).thenReturn(
        Response.success(emptyList())
    )
}
