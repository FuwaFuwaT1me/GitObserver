package com.example.gitobserver.domain.usecase

import android.content.Context
import com.example.gitobserver.data.repository.SharedPrefStorageRepositoryImpl
import com.example.gitobserver.domain.model.UserDetails
import org.json.JSONObject
import javax.inject.Inject

class SharedPrefStorageUseCase @Inject constructor(
    private val sharedPrefStorageRepositoryImpl: SharedPrefStorageRepositoryImpl
) {

    fun saveUserDetails(userDetails: UserDetails) {
        sharedPrefStorageRepositoryImpl.saveUserDetails(
            userDetails = userDetails
        )
    }

    fun getUserDetails(): UserDetails {
        return sharedPrefStorageRepositoryImpl.getUserDetails()
    }

    fun saveLanguageColors(colors: String) {
        sharedPrefStorageRepositoryImpl.setLanguageColors(colors)
    }

    fun getLanguageColors(): String {
        return sharedPrefStorageRepositoryImpl.getLanguageColors()
    }
}
