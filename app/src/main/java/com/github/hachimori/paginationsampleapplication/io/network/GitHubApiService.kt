package com.github.hachimori.paginationsampleapplication.io.network

import com.github.hachimori.paginationsampleapplication.io.network.responsemodels.UserResponse
import com.github.hachimori.paginationsampleapplication.utils.GITHUB_API_USER_PER_PAGE
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


/**
 * GitHub API
 *   - https://docs.github.com/en/rest/users/users
 */
interface GitHubApiService {

    @GET("users")
    suspend fun getUserList(
        @Query("since") since: Int,
        @Query("per_page") per_page: Int = GITHUB_API_USER_PER_PAGE
    ): Response<List<UserResponse>>

    companion object {
        const val GITHUB_API_ENDPOINT = "https://api.github.com/"
    }
}
