package com.github.hachimori.paginationsampleapplication.di

import android.content.Context
import androidx.room.Room
import com.github.hachimori.paginationsampleapplication.io.database.GitHubDatabase
import com.github.hachimori.paginationsampleapplication.utils.PAGINATION_SAMPLE_DB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideGitHubDatabase(@ApplicationContext context: Context): GitHubDatabase =
        Room.databaseBuilder(context, GitHubDatabase::class.java, PAGINATION_SAMPLE_DB)
            .fallbackToDestructiveMigration()
            .build()
}
