package com.github.hachimori.paginationsampleapplication.ui.userlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import com.github.hachimori.paginationsampleapplication.usecases.GetUserPagingDataFlowUsecase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    private val getUserPagingDataFlowUsecase: GetUserPagingDataFlowUsecase
) : ViewModel() {

    val pagingDataFlow: Flow<PagingData<User>> =
        getUserPagingDataFlowUsecase
            .getUserPagingDataFlow()
            .cachedIn(viewModelScope)

    private val _hasNetworkErrorState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasNetworkErrorState: StateFlow<Boolean> = _hasNetworkErrorState

    private val _generalErrorFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    val generalErrorFlow: SharedFlow<Unit> = _generalErrorFlow

}
