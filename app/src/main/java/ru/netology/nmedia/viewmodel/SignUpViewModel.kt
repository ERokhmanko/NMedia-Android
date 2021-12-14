package ru.netology.nmedia.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.*
import androidx.lifecycle.*
import okhttp3.MultipartBody
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dto.User
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import ru.netology.nmedia.model.FeedModelState
import ru.netology.nmedia.model.PhotoModel
import java.io.File
import java.io.IOException

class SignUpViewModel : ViewModel() {
    private val _data = MutableLiveData<User>()
    val data: LiveData<User>
        get() = _data

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    private val noPhoto = PhotoModel()
    private val _photo = MutableLiveData(noPhoto)
    val photo: LiveData<PhotoModel>
        get() = _photo

    fun registrationUser(login: String, password: String, name: String) {
        viewModelScope.launch {
            try {
                val response = PostApi.retrofitService.registrationUser(login, password, name)
                if (!response.isSuccessful) {
                    throw ApiError(response.code(), response.message())
                }
                val body = response.body() ?: throw Exception()
                _data.value = body
            } catch (e: IOException) {
                throw NetworkError
            } catch (e: Exception) {
                throw UnknownError()
            }
        }

    }

    fun changePhoto(uri: Uri?, file: File?) {
        _photo.value = PhotoModel(uri, file)
    }

}
