package com.example.gitobserver.domain.usecase

import android.content.Context
import com.example.gitobserver.data.repository.SharedPrefStorageRepositoryImpl
import com.example.gitobserver.domain.model.UserDetails
import javax.inject.Inject

class SharedPrefStorageUseCase @Inject constructor(
    private val sharedPrefStorageRepositoryImpl: SharedPrefStorageRepositoryImpl
) {

    fun save(userDetails: UserDetails) {
        sharedPrefStorageRepositoryImpl.saveUserDetails(
            userDetails = userDetails
        )
    }

    fun get(): UserDetails {
        return sharedPrefStorageRepositoryImpl.getUserDetails()
    }
}
