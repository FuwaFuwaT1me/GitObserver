package com.example.gitobserver.presentation.repositories

import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.gitobserver.app.App
import com.example.gitobserver.domain.model.Resource
import com.example.gitobserver.domain.usecase.GitHubRepositoriesUseCase
import com.example.gitobserver.domain.usecase.SharedPrefStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val gitHubRepositoriesUseCase: GitHubRepositoriesUseCase,
    private val sharedPrefStorageUseCase: SharedPrefStorageUseCase
) : ViewModel() {

    fun getGitHubRepositories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = gitHubRepositoriesUseCase.getRepositories(
                        sharedPrefStorageUseCase.get().login
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occurred"))
        }
    }
}
