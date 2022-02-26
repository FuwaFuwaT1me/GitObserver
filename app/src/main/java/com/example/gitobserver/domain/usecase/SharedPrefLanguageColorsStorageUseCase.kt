package com.example.gitobserver.domain.usecase

import com.example.gitobserver.data.repository.SharedPrefLanguageColorsStorageRepositoryImpl
import javax.inject.Inject

class SharedPrefLanguageColorsStorageUseCase @Inject constructor(
    private val sharedPrefLanguageColorsStorageRepositoryImpl: SharedPrefLanguageColorsStorageRepositoryImpl
) {

    fun saveLanguageColors(colors: String) {
        sharedPrefLanguageColorsStorageRepositoryImpl.setLanguageColors(colors)
    }

    fun getLanguageColors(): String {
        return sharedPrefLanguageColorsStorageRepositoryImpl.getLanguageColors()
    }
}