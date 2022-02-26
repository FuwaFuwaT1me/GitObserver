package com.example.gitobserver.presentation.repositories.adapter

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gitobserver.R
import com.example.gitobserver.databinding.RepositoriesListItemBinding
import com.example.gitobserver.domain.model.GitHubRepository
import com.example.gitobserver.domain.usecase.SharedPrefLanguageColorsStorageUseCase
import com.example.gitobserver.domain.usecase.SharedPrefUserStorageUseCase
import com.example.gitobserver.presentation.repositories.OnRepositoryClickListener
import org.json.JSONObject

class RepositoriesRecyclerViewHolder(
    inflater: LayoutInflater,
    parent: ViewGroup,
    private val sharedPrefLanguageColorsStorageUseCase: SharedPrefLanguageColorsStorageUseCase,
    private val onRepositoryClickListener: OnRepositoryClickListener
) : RecyclerView.ViewHolder(inflater.inflate(R.layout.repositories_list_item, parent, false)) {

    private val mBinding by viewBinding(RepositoriesListItemBinding::bind)

    fun bind(gitHubRepository: GitHubRepository) {
        Log.d("AAA", "$gitHubRepository")
        mBinding.repositoryItemName.text = gitHubRepository.name
        mBinding.repositoryItemLanguage.text = gitHubRepository.language
        try {
            val color =
                JSONObject(sharedPrefLanguageColorsStorageUseCase.getLanguageColors())
                    .get(mBinding.repositoryItemLanguage.text.toString()).toString()

            mBinding.repositoryItemLanguage.setTextColor(
                Color.parseColor(color)
            )
        } catch (exception: Exception) {
        }
        sharedPrefLanguageColorsStorageUseCase.getLanguageColors()

        mBinding.repositoryItemDescription.text = gitHubRepository.description


        itemView.setOnClickListener {
            onRepositoryClickListener.onclick(gitHubRepository)
        }
    }
}