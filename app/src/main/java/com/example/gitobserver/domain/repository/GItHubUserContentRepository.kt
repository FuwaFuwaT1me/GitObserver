package com.example.gitobserver.domain.repository

import com.example.gitobserver.domain.model.GitHubRepositoryContents

interface GItHubUserContentRepository {

    suspend fun getRepositoryContents(
        username: String,
        repository: String
    ): String
}