package com.example.gitobserver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubRepositoryContents(
    val content: String
): Parcelable
