package com.example.gitobserver.di

import com.example.gitobserver.data.remote.api.GitHubLoginApiService
import com.example.gitobserver.data.remote.client.GitHubApiClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteModule {

    @Provides
    @Singleton
    fun provideGitHubLoginClient(): GitHubLoginApiService {
        return GitHubApiClient.buildGitHubLoginApi()
    }
}