package com.github.hachimori.paginationsampleapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.github.hachimori.paginationsampleapplication.R
import com.github.hachimori.paginationsampleapplication.databinding.ActivityMainBinding
import com.github.hachimori.paginationsampleapplication.io.network.GitHubApiService
import com.github.hachimori.paginationsampleapplication.io.network.getBodyOrThrow
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
