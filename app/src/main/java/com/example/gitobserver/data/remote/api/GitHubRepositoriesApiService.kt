package com.example.gitobserver.data.remote.api

import com.example.gitobserver.domain.model.GitHubRepository
import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubRepositoriesApiService {

    @GET("users/{username}/repos")
    suspend fun getRepositoriesByUsername(
        @Path("username") username: String
    ): List<GitHubRepository>
}
