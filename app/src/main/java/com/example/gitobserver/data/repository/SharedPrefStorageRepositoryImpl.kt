package com.example.gitobserver.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.gitobserver.domain.model.UserDetails
import com.example.gitobserver.domain.repository.StorageRepository
import com.google.gson.Gson
import org.json.JSONObject
import javax.inject.Inject

private const val USER_DETAILS = "user_details"
private const val LANGUAGE_COLORS = "language_colors"

class SharedPrefStorageRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : StorageRepository {

    override fun saveUserDetails(userDetails: UserDetails) {
        val userDetailsJson = Gson().toJson(userDetails)

        sharedPreferences
            .edit()
            .putString(USER_DETAILS, userDetailsJson)
            .apply()
    }

    override fun getUserDetails(): UserDetails {
        val userDetailsJson = sharedPreferences.getString(USER_DETAILS, "")

        return Gson().fromJson(userDetailsJson, UserDetails::class.java)
    }

    override fun setLanguageColors(colors: String) {
        sharedPreferences
            .edit()
            .putString(LANGUAGE_COLORS, colors)
            .apply()
    }

    override fun getLanguageColors(): String {
        return sharedPreferences.getString(LANGUAGE_COLORS, null) ?: ""
    }
}
