package com.example.gitobserver.domain.repository

import com.example.gitobserver.domain.model.UserDetails

interface StorageUserRepository {

    fun saveUserDetails(userDetails: UserDetails?)

    fun getUserDetails(): UserDetails?

    fun exitUser()
}
