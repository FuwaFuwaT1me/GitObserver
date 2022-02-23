package com.example.gitobserver.domain.repository

import android.content.Context
import com.example.gitobserver.domain.model.UserDetails

interface StorageRepository {

    fun saveUserDetails(userDetails: UserDetails)

    fun getUserDetails(): UserDetails
}
