package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import androidx.lifecycle.*
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.api.UserApi
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import ru.netology.nmedia.model.FeedModelState
import java.io.IOException

class SignInViewModel : ViewModel() {
    private val _data = MutableLiveData<User>()
    val data: LiveData<User>
        get() = _data

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    fun authUser(login: String, password: String) {
        viewModelScope.launch {
            try {
                val response = PostApi.retrofitService.authUser(login, password)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw Exception()
                _data.value = body
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                _dataState.postValue(FeedModelState(errorLogin = true))

            }
        }

    }
}
