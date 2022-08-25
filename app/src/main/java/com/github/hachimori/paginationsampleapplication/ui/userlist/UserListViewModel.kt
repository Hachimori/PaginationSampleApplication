package com.github.hachimori.paginationsampleapplication.ui.userlist

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class UserListViewModel @Inject constructor(
    // private val getPagingDataFlowUsecase: GetPagingDataFlowUsecase
) : ViewModel() {

    // val pagingDataFlow: Flow<PagingData<User>>

    private val _hasNetworkErrorState: MutableStateFlow<Boolean> = MutableStateFlow(false)
    val hasNetworkErrorState: StateFlow<Boolean> = _hasNetworkErrorState

    private val _generalErrorFlow: MutableSharedFlow<Unit> = MutableSharedFlow()
    val generalErrorFlow: SharedFlow<Unit> = _generalErrorFlow

}
