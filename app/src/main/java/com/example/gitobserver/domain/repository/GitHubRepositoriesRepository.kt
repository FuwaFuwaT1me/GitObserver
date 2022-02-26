package com.example.gitobserver.domain.repository

import com.example.gitobserver.domain.model.GitHubRepository
import com.example.gitobserver.domain.model.GitHubRepositoryContents

interface GitHubRepositoriesRepository {

    suspend fun getRepositories(username: String): List<GitHubRepository>
}
