package com.example.gitobserver.domain.repository

import androidx.lifecycle.LiveData
import com.example.gitobserver.domain.model.UserDetails

interface LoginRepository {

    suspend fun requestLogin(token: String): UserDetails
}