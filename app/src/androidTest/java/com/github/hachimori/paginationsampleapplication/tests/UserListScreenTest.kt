package com.github.hachimori.paginationsampleapplication.tests

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.github.hachimori.paginationsampleapplication.HiltTestActivity
import com.github.hachimori.paginationsampleapplication.setupErrorMock
import com.github.hachimori.paginationsampleapplication.setupMock
import com.github.hachimori.paginationsampleapplication.ui.userlist.UserListScreen
import com.github.hachimori.paginationsampleapplication.ui.userlist.UserListViewModel
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class UserListScreenTest {

    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeTestRule = createAndroidComposeRule<HiltTestActivity>()

    @BindValue
    val fakeUserListViewModel: UserListViewModel = mock(UserListViewModel::class.java)

    @Test
    fun testUserListContent() {
        fakeUserListViewModel.setupMock()

        composeTestRule.setContent {
            UserListScreen()
        }

        composeTestRule
            .onNodeWithText("login_1")
            .assertIsDisplayed()
    }

    @Test
    fun testUserListErrorContent() {
        fakeUserListViewModel.setupErrorMock()

        composeTestRule.setContent {
            UserListScreen()
        }

        composeTestRule
            .onNodeWithText("Retry")
            .assertIsDisplayed()
    }
}
