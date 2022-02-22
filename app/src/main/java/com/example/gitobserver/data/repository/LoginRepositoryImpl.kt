package com.example.gitobserver.data.repository

import androidx.lifecycle.LiveData
import com.example.gitobserver.data.remote.api.ApiHelper
import com.example.gitobserver.data.remote.api.GitHubLoginApiService
import com.example.gitobserver.domain.model.UserDetails
import com.example.gitobserver.domain.repository.LoginRepository
import javax.inject.Inject

class LoginRepositoryImpl @Inject constructor(
    //private val gitHubLoginApiService: GitHubLoginApiService
    private val apiHelper: ApiHelper
): LoginRepository {

//    override suspend fun requestLogin(token: String): LiveData<UserDetails> {
//        return gitHubLoginApiService.login(token)
//    }

    override suspend fun requestLogin(token: String) = apiHelper.login(token)
}