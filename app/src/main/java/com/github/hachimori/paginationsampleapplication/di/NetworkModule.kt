package com.github.hachimori.paginationsampleapplication.di

import androidx.viewbinding.BuildConfig
import com.github.hachimori.paginationsampleapplication.io.network.GitHubApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        // Display the result of Web API request on Logcat
        if (BuildConfig.DEBUG) {
            httpClientBuilder.addInterceptor(
                HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BASIC
                }
            )
        }

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(GitHubApiService.GITHUB_API_ENDPOINT)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideGitHubApiService(retrofit: Retrofit): GitHubApiService =
        retrofit.create(GitHubApiService::class.java)
}
