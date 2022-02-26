package com.example.gitobserver.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GitHubLicense(
    val key: String
): Parcelable
