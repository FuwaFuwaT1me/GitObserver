package com.example.gitobserver.domain.repository

import com.example.gitobserver.domain.model.UserDetails

interface StorageRepository {

    fun saveUserDetails(userDetails: UserDetails)

    fun getUserDetails(): UserDetails

    fun setLanguageColors(colors: String)

    fun getLanguageColors(): String
}
