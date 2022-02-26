package com.example.gitobserver.data.repository

import android.content.SharedPreferences
import com.example.gitobserver.domain.model.UserDetails
import com.example.gitobserver.domain.repository.StorageUserRepository
import com.google.gson.Gson
import javax.inject.Inject

private const val USER_DETAILS = "user_details"

class SharedPrefUserStorageRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : StorageUserRepository {

    override fun saveUserDetails(userDetails: UserDetails?) {
        val userDetailsJson = Gson().toJson(userDetails)

        sharedPreferences
            .edit()
            .putString(USER_DETAILS, userDetailsJson)
            .apply()
    }

    override fun getUserDetails(): UserDetails? {
        val userDetailsJson = sharedPreferences.getString(USER_DETAILS, null) ?: return null

        return Gson().fromJson(userDetailsJson, UserDetails::class.java)
    }

    override fun exitUser() {
        sharedPreferences
            .edit()
            .putString(USER_DETAILS, null)
            .apply()
    }
}
