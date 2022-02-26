package com.example.gitobserver.data.repository

import android.content.SharedPreferences
import com.example.gitobserver.domain.repository.StorageLanguageColorsRepository
import javax.inject.Inject

private const val LANGUAGE_COLORS = "language_colors"

class SharedPrefLanguageColorsStorageRepositoryImpl @Inject constructor(
    private val sharedPreferences: SharedPreferences
) :StorageLanguageColorsRepository {

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