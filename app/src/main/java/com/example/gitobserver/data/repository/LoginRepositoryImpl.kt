package com.example.gitobserver.data.repository

import com.example.gitobserver.data.remote.api.GitHubLoginApiService
import com.example.gitobserver.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    private val gitHubLoginApiService: GitHubLoginApiService
): LoginRepository {

    override suspend fun requestLogin(token: String) = gitHubLoginApiService.login(token)
}
