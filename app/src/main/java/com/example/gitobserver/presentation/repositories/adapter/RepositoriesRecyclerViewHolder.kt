package com.example.gitobserver.presentation.repositories.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.view.menu.ListMenuItemView
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gitobserver.R
import com.example.gitobserver.databinding.RepositoriesListItemBinding
import com.example.gitobserver.domain.model.GitHubRepository

class RepositoriesRecyclerViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
): RecyclerView.ViewHolder(inflater.inflate(R.layout.repositories_list_item, parent, false)) {

    private val mBinding by viewBinding(RepositoriesListItemBinding::bind)

    fun bind(gitHubRepository: GitHubRepository) {
        mBinding.repositoryItemName.text = gitHubRepository.name
        mBinding.repositoryItemLanguage.text = gitHubRepository.language
        mBinding.repositoryItemDescription.text = gitHubRepository.description
    }
}
