package com.example.gitobserver.presentation.repositories

import com.example.gitobserver.domain.model.GitHubRepository

interface OnRepositoryClickListener {

    fun onclick(gitHubRepository: GitHubRepository)
}