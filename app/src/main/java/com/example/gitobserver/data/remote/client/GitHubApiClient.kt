package com.example.gitobserver.data.remote.client

import com.example.gitobserver.data.remote.api.GitHubLoginApiService
import com.example.gitobserver.data.remote.api.GitHubRepositoriesApiService
import com.example.gitobserver.data.remote.api.GitHubUserContentApiService
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

private const val BASE_URL = "https://api.github.com/"
private const val BASE_URL_CONTENT = "https://raw.githubusercontent.com/"

object GitHubApiClient {

    private fun getGitHubApiInstance(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun getGitHubUserContentInstance(): Retrofit {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        return Retrofit.Builder()
            .baseUrl(BASE_URL_CONTENT)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Synchronized
    fun buildGitHubLoginApi(): GitHubLoginApiService {
        return getGitHubApiInstance().create(GitHubLoginApiService::class.java)
    }

    @Synchronized
    fun buildGitHubRepositoriesApi(): GitHubRepositoriesApiService {
        return getGitHubApiInstance().create(GitHubRepositoriesApiService::class.java)
    }

    @Synchronized
    fun buildGitHubUserContentApi(): GitHubUserContentApiService {
        return getGitHubUserContentInstance().create(GitHubUserContentApiService::class.java)
    }
}
