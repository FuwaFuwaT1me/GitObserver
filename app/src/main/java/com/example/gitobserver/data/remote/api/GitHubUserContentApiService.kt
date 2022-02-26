package com.example.gitobserver.data.remote.api

import retrofit2.http.GET
import retrofit2.http.Path

interface GitHubUserContentApiService {

    @GET("{username}/{repository}/master/README.md")
    suspend fun getRepositoryContents(
        @Path("username") username: String,
        @Path("repository") repository: String
    ): String
}