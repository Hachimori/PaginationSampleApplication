package com.github.hachimori.paginationsampleapplication.ui.userlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.github.hachimori.paginationsampleapplication.R
import com.github.hachimori.paginationsampleapplication.io.appmodels.User
import kotlinx.coroutines.flow.Flow

@Composable
fun UserListScreen() {
    val userListViewModel = hiltViewModel<UserListViewModel>()
    val pagingDataFlow = userListViewModel.pagingDataFlow

    UserListContent(pagingDataFlow)
}

@Composable
fun UserListContent(
    pagingDataFlow: Flow<PagingData<User>>
) {
    val lazyUserItems = pagingDataFlow.collectAsLazyPagingItems()

    LazyColumn {
        items(lazyUserItems.itemCount) { index ->
            lazyUserItems[index]?.let {
                UserItem(it)
                Divider(color = Color.Gray, thickness = 1.dp)
            }
        }
        with(lazyUserItems) {
            if (loadState.refresh is LoadState.Loading || loadState.append is LoadState.Loading) {
                item {
                    LoadingItem()
                }
            }
            else if (loadState.refresh is LoadState.Error || loadState.append is LoadState.Error) {
                item {
                    RetryItem(onRetry = {
                        retry()
                    })
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Row(modifier = Modifier.height(100.dp)) {
        AsyncImage(
            modifier = Modifier
                .fillMaxHeight()
                .padding(8.dp),
            model = ImageRequest.Builder(LocalContext.current)
                .data(user.avatarUrl)
                .crossfade(true)
                .build(),
            contentScale = ContentScale.FillHeight,
            contentDescription = ""
        )
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            text = user.login,
            color = Color.Black
        )
    }
}

@Composable
fun LoadingItem() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        CircularProgressIndicator()
    }
}

@Composable
fun RetryItem(onRetry: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
        Button(onClick = {
            onRetry()
        }) {
            Text(stringResource(R.string.retry))
        }
    }
}