package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.toEntity
import androidx.lifecycle.map
import ru.netology.nmedia.entity.toDto
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.error.NetworkError
import ru.netology.nmedia.error.UnknownError
import java.io.IOException


class PostRepositoryImpl(private val postDao: PostDao) : PostRepository {

    override val data: LiveData<List<Post>> = postDao.getAll().map { it.toDto() }

    override suspend fun getAll() {
        try {
            val response = PostApi.retrofitService.getAll()
            if (!response.isSuccessful) {
                throw ApiError(response.code(), response.message())
            }
            val body = response.body() ?: throw Exception()
            postDao.insert(body.toEntity())
        } catch (e: IOException) {
            throw NetworkError
        } catch (e: Exception) {
            throw UnknownError
        }
    }

    override suspend fun likedById(id: Long) {
        PostApi.retrofitService.likedById(id)
    }

    override suspend fun unlikeById(id: Long) {
        PostApi.retrofitService.unlikeById(id)
    }

    override suspend fun removeById(id: Long) {
        PostApi.retrofitService.removeById(id)
    }

    override suspend fun save(post: Post) {
        PostApi.retrofitService.save(post)
    }

    override suspend fun shareById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDraft(draft: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getDraft() {
        TODO("Not yet implemented")
    }

}


