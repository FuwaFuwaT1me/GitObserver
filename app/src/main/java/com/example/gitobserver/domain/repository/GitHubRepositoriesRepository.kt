package com.example.gitobserver.domain.repository

import com.example.gitobserver.domain.model.GitHubRepository

interface GitHubRepositoriesRepository {

    suspend fun getRepositories(username: String): List<GitHubRepository>
}
