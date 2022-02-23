package com.example.gitobserver.utils

import android.content.Context
import com.example.gitobserver.domain.model.UserDetails
import com.google.gson.Gson

object LoginPreference {

    private const val LOGIN_PREFERENCE = "login_preference"
    private const val USER_DETAILS = "user_details"

    fun saveUserDetails(context: Context, userDetails: UserDetails) {
        val userDetailsJson = Gson().toJson(userDetails)

        context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE)
            .edit()
            .putString(USER_DETAILS, userDetailsJson)
            .apply()
    }

    fun getUserDetails(context: Context, userDetails: UserDetails): UserDetails {
        val userDetailsJson = context.getSharedPreferences(LOGIN_PREFERENCE, Context.MODE_PRIVATE)
            .getString(USER_DETAILS, "")
        return Gson().fromJson(userDetailsJson, UserDetails::class.java)
    }
}
