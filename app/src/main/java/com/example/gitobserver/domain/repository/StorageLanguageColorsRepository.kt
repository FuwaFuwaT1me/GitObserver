package com.example.gitobserver.domain.repository

interface StorageLanguageColorsRepository {

    fun setLanguageColors(colors: String)

    fun getLanguageColors(): String
}