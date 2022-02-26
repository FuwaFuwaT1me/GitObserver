package com.example.gitobserver.data.repository

import com.example.gitobserver.data.remote.api.GitHubRepositoriesApiService
import com.example.gitobserver.domain.model.GitHubRepository
import com.example.gitobserver.domain.repository.GitHubRepositoriesRepository
import javax.inject.Inject

class GitHubRepositoriesRepositoryImpl @Inject constructor(
    private val gitHubRepositoriesApiService: GitHubRepositoriesApiService
) : GitHubRepositoriesRepository {

    override suspend fun getRepositories(username: String): List<GitHubRepository> {
        return gitHubRepositoriesApiService.getRepositoriesByUsername(username = username)
    }
}
