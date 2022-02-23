package com.example.gitobserver.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubRepository(
    val id: Int,
    val name: String,
    @SerializedName("html_url") val url: String,
    val description: String,
    val language: String
): Parcelable
