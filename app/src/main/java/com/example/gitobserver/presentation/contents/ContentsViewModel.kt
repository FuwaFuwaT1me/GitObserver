package com.example.gitobserver.presentation.contents

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.gitobserver.domain.model.Resource
import com.example.gitobserver.domain.usecase.GitHubUserContentUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class ContentsViewModel @Inject constructor(
    private val gitHubUserContentUseCase: GitHubUserContentUseCase
) : ViewModel() {

    fun getRepositoryContent(username: String, repository: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        try {
            emit(
                Resource.success(
                    data = gitHubUserContentUseCase.getRepositoryContents(
                        username = username,
                        repository = repository
                    )
                )
            )
        } catch (exception: Exception) {
            Log.d("BBB", "${exception.message}")
            emit(Resource.error(data = null, message = exception.message ?: "Error occurred"))
        }
    }
}