package com.example.gitobserver.data.remote.api

import androidx.lifecycle.LiveData
import com.example.gitobserver.domain.model.UserDetails
import retrofit2.http.GET
import retrofit2.http.Header

interface GitHubLoginApiService {

    @GET("user")
    suspend fun login(@Header("Authorization") token: String): UserDetails
}