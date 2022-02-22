package com.example.gitobserver.data.remote.api

import javax.inject.Inject

class ApiHelper @Inject constructor(
    private val gitHubLoginApiService: GitHubLoginApiService
) {

    suspend fun login(token: String) = gitHubLoginApiService.login(token)
}