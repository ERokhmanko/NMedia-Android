package ru.netology.nmedia.repository

import androidx.lifecycle.LiveData
import retrofit2.Call
import retrofit2.Response
import ru.netology.nmedia.api.PostApi
import ru.netology.nmedia.dao.PostDao
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.entity.PostEntity
import ru.netology.nmedia.entity.toEntity
import java.lang.RuntimeException
import androidx.lifecycle.map


class PostRepositoryImpl(val postDao: PostDao) : PostRepository {

    override val data: LiveData<List<Post>> = postDao.getAll().map{it.toDto}

    override suspend fun getAll() {
        val response = PostApi.retrofitService.getAll()
        if (!response.isSuccessful){

        }
        val data = response.body() ?: throw Exception()
        postDao.insert(data.toEntity())
    }

    override suspend fun likedById(id: Long){
        PostApi.retrofitService.likedById(id)
    }

    override suspend fun unlikeById(id: Long){
        PostApi.retrofitService.unlikeById(id)
    }

    override suspend fun removeById(id: Long){
        PostApi.retrofitService.removeById(id)
    }

    override suspend fun save(post: Post){
        PostApi.retrofitService.save(post)
    }

    override suspend fun shareById(id: Long) {
        TODO("Not yet implemented")
    }

    override suspend fun saveDraft(draft: String?) {
        TODO("Not yet implemented")
    }

    override suspend fun getDraft(): String? {
        TODO("Not yet implemented")
    }

}


