package com.github.hachimori.paginationsampleapplication

import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

/**
 * HiltTestActivity is used inside launchFragmentInHiltContainer() / createAndroidComposeRule<HiltTestActivity>() to use Hilt during testing.
 *
 * This file is directly taken from following url:
 *   - https://github.com/android/architecture-samples/blob/views-hilt/app/src/debug/java/com/example/android/architecture/blueprints/todoapp/HiltTestActivity.kt
 *
 * Reference:
 *   - https://developer.android.com/training/dependency-injection/hilt-testing#launchfragment
 *   - https://stackoverflow.com/a/69765600/13665460
 */
@AndroidEntryPoint
class HiltTestActivity : AppCompatActivity()
