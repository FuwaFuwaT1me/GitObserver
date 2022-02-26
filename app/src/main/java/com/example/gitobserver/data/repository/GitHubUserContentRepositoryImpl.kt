package com.example.gitobserver.data.repository

import com.example.gitobserver.data.remote.api.GitHubUserContentApiService
import com.example.gitobserver.domain.model.GitHubRepositoryContents
import com.example.gitobserver.domain.repository.GItHubUserContentRepository
import javax.inject.Inject

class GitHubUserContentRepositoryImpl @Inject constructor(
    private val gitHubUserContentApiService: GitHubUserContentApiService
): GItHubUserContentRepository {

    override suspend fun getRepositoryContents(
        username: String,
        repository: String
    ): String {
        return gitHubUserContentApiService.getRepositoryContents(
            username = username,
            repository = repository
        )
    }
}