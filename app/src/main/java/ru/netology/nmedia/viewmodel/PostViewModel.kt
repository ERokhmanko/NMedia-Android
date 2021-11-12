package ru.netology.nmedia.viewmodel

import android.app.Application
import androidx.lifecycle.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.model.FeedModel
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl
import ru.netology.nmedia.utils.SingleLiveEvent
import kotlinx.coroutines.*
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.model.FeedModelState

private val emptyPost = Post(
    id = 0,
    author = "",
    authorAvatar = "",
    content = "",
    published = "",
    likes = 0,
    likedByMe = false,
    share = false,
    sharesCount = 0
)

class PostViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    private val _data = repository.data.map { FeedModel(posts = it, empty = it.isEmpty()) }
    val data: LiveData<FeedModel>
        get() = _data

    private val _dataState = MutableLiveData<FeedModelState>()
    val dataState: LiveData<FeedModelState>
        get() = _dataState

    val edited = MutableLiveData(emptyPost)
    private val _postCreated = SingleLiveEvent<Unit>()
    val postCreated: LiveData<Unit>
        get() = _postCreated
    val old = _data.value?.posts.orEmpty()
    val avatars = emptyList<String>()

    init {
        loadPosts()
    }

    fun loadPosts() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(loading = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun refreshPosts() = viewModelScope.launch {
        try {
            _dataState.value = FeedModelState(refreshing = true)
            repository.getAll()
            _dataState.value = FeedModelState()
        } catch (e: Exception) {
            _dataState.value = FeedModelState(error = true)
        }
    }

    fun save() {
        edited.value?.let {
//        _postCreated.value = Unit
        viewModelScope.launch {
            try {
                repository.save(it)
                _dataState.value = FeedModelState()
            } catch (e: Exception) {
                _dataState.value = FeedModelState(error = true)
            }
        }
    }
        edited.value = emptyPost
    }

    fun changeContent(content: String) {
        edited.value?.let {
            val text = content.trim()
            if (it.content != text)
                edited.value = it.copy(content = text)
        }
    }

    fun edit(post: Post) {
        edited.value = post
    }

    fun likeById(id: Long) {
//        repository.likedById(id, object : PostRepository.PostCallback {
//            override fun onSuccess(post: Post) {
//                _data.postValue(
//                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
//                        .map {
//                            if (it.id == id) post else it
//                        }
//                    )
//                )
//            }
//
//            override fun onError(e: Exception) {
//                _data.postValue(_data.value?.copy(posts = old))
//            }
//        })
    }


    fun unlikeById(id: Long) {
//        repository.unlikeById(id, object : PostRepository.PostCallback {
//            override fun onSuccess(post: Post) {
//                _data.postValue(
//                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
//                        .map {
//                            if (it.id == id) post else it
//                        }
//                    )
//                )
//            }
//
//            override fun onError(e: Exception) {
//                _data.postValue(_data.value?.copy(posts = old))
//            }
//        })
    }

    fun shareById(id: Long) {
//            repository.shareById(id)
    }

    fun removeById(id: Long) {
//        repository.removeById(id, object : PostRepository.IdCall {
//            override fun onSuccess(unit: Unit) {
//                _data.postValue(
//                    _data.value?.copy(posts = _data.value?.posts.orEmpty()
//                        .filter { it.id != id }
//                    )
//                )
//            }
//
//            override fun onError(e: Exception) {
//                _data.postValue(_data.value?.copy(posts = old))
//            }
//        })
    }


    fun cancelEditing() = edited.value?.let {
//        repository.cancelEditing(it)
    }


//    fun saveDraft(draft: String?) = repository.saveDraft(draft)
//    fun getDraft(): String? = repository.getDraft()

}
