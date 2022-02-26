package com.example.gitobserver.domain.usecase

import com.example.gitobserver.data.repository.GitHubUserContentRepositoryImpl
import com.example.gitobserver.domain.model.GitHubRepositoryContents
import javax.inject.Inject

class GitHubUserContentUseCase @Inject constructor(
    private val gitHubUserContentRepositoryImpl: GitHubUserContentRepositoryImpl
) {

    suspend fun getRepositoryContents(
        username: String,
        repository: String
    ): String {
        return gitHubUserContentRepositoryImpl.getRepositoryContents(
            username = username,
            repository = repository
        )
    }
}