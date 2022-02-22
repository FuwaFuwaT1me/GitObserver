package com.example.gitobserver.domain.usecase

import androidx.lifecycle.LiveData
import com.example.gitobserver.data.repository.LoginRepositoryImpl
import com.example.gitobserver.domain.model.UserDetails
import javax.inject.Inject

class GitHubLoginUseCase @Inject constructor(
    private val loginRepositoryImpl: LoginRepositoryImpl
) {

    suspend fun requestLogin(token: String): UserDetails {
        return loginRepositoryImpl.requestLogin(token)
    }
}