package com.example.gitobserver.data.remote.client

import com.example.gitobserver.data.remote.api.GitHubLoginApiService
import com.example.gitobserver.data.remote.api.GitHubRepositoriesApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://api.github.com/"

object GitHubApiClient {

    private fun getInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Synchronized
    fun buildGitHubLoginApi(): GitHubLoginApiService {
        return getInstance().create(GitHubLoginApiService::class.java)
    }

    @Synchronized
    fun buildGitHubRepositoriesApi(): GitHubRepositoriesApiService {
        return getInstance().create(GitHubRepositoriesApiService::class.java)
    }
}
