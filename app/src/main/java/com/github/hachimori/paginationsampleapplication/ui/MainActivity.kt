package com.github.hachimori.paginationsampleapplication.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.github.hachimori.paginationsampleapplication.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
