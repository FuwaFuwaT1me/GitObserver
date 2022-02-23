package com.example.gitobserver.di

import android.content.Context
import android.content.SharedPreferences
import com.example.gitobserver.data.remote.api.GitHubLoginApiService
import com.example.gitobserver.data.remote.client.GitHubApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val SHARED_PREFERENCES = "shared_preferences"

@Module
@InstallIn(SingletonComponent::class)
class SharedPreferencesModule {

    @Provides
    @Singleton
    fun provideSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
    }
}
