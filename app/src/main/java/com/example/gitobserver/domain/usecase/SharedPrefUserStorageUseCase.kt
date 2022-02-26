package com.example.gitobserver.domain.usecase

import com.example.gitobserver.data.repository.SharedPrefUserStorageRepositoryImpl
import com.example.gitobserver.domain.model.UserDetails
import javax.inject.Inject

class SharedPrefUserStorageUseCase @Inject constructor(
    private val sharedPrefStorageRepositoryImpl: SharedPrefUserStorageRepositoryImpl
) {

    fun saveUserDetails(userDetails: UserDetails?) {
        sharedPrefStorageRepositoryImpl.saveUserDetails(
            userDetails = userDetails
        )
    }

    fun getUserDetails(): UserDetails? {
        return sharedPrefStorageRepositoryImpl.getUserDetails()
    }

    fun exitUser() {
        sharedPrefStorageRepositoryImpl.exitUser()
    }
}
