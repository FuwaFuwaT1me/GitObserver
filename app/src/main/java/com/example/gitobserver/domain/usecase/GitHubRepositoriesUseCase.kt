package com.example.gitobserver.domain.usecase

import com.example.gitobserver.data.repository.GitHubRepositoriesRepositoryImpl
import com.example.gitobserver.domain.model.GitHubRepository
import javax.inject.Inject

class GitHubRepositoriesUseCase @Inject constructor(
    private val gitHubRepositoriesRepositoryImpl: GitHubRepositoriesRepositoryImpl
) {

    suspend fun getRepositories(username: String): List<GitHubRepository> {
        return gitHubRepositoriesRepositoryImpl.getRepositories(username = username)
    }
}
