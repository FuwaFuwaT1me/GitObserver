package com.example.gitobserver.presentation.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.gitobserver.domain.model.Resource
import com.example.gitobserver.domain.model.UserDetails
import com.example.gitobserver.domain.usecase.GitHubLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val gitHubLoginUseCase: GitHubLoginUseCase
): ViewModel() {

    fun login(token: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading(data = null))
        kotlinx.coroutines.delay(1000)
        try {
            emit(Resource.success(data = gitHubLoginUseCase.requestLogin("Bearer $token")))
        } catch (exception: Exception) {
            emit(Resource.error(data = null, message = exception.message ?: "Error occurred"))
        }
    }
}