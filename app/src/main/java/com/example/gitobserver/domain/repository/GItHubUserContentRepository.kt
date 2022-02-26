package com.example.gitobserver.domain.repository

interface GItHubUserContentRepository {

    suspend fun getRepositoryContents(
        username: String,
        repository: String
    ): String
}