package com.github.hachimori.paginationsampleapplication.usecases

import androidx.paging.PagingData
import com.github.hachimori.paginationsampleapplication.di.IoDispatcher
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.repositories.GitHubRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUserPagingDataFlowUsecase @Inject constructor(
    private val gitHubRepository: GitHubRepository
) {
    fun getUserPagingDataFlow(): Flow<PagingData<User>> =
        gitHubRepository.getUserPagingDataFlow()
}