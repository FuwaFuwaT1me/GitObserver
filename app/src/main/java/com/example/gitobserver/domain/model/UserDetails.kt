package com.example.gitobserver.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetails(
    val login: String,
    val id: Int
): Parcelable