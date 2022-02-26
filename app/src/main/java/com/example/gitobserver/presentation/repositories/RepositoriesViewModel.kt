package com.example.gitobserver.presentation.repositories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.gitobserver.domain.model.Resource
import com.example.gitobserver.domain.usecase.GitHubRepositoriesUseCase
import com.example.gitobserver.domain.usecase.SharedPrefUserStorageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class RepositoriesViewModel @Inject constructor(
    private val gitHubRepositoriesUseCase: GitHubRepositoriesUseCase,
    private val sharedPrefStorageUseCase: SharedPrefUserStorageUseCase
) : ViewModel() {

    fun getGitHubRepositories() = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = gitHubRepositoriesUseCase.getRepositories(
                        sharedPrefStorageUseCase.getUserDetails()!!.login
                    )
                )
            )
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occurred"))
        }
    }
}
