package ru.netology.nmedia.api

import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.repository.PostRepository

private const val BASE_URL = "http://10.0.2.2:9999/api/"
private val retrofit = Retrofit.Builder()
    .addConverterFactory(GsonConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface PostApiService {
    @GET("posts")
    suspend fun getAll() : Response<List<Post>>

    @POST("posts/{id}/likes")
    suspend fun likedById(@Path("id") id: Long) : Response<Post>

    @DELETE("posts/{id}/likes")
    suspend fun unlikeById(@Path("id") id: Long) : Response<Post>

    @DELETE("posts/{id}")
    suspend fun removeById(@Path("id") id: Long) : Response<Unit>

    @POST("posts")
    suspend fun save(@Body post: Post): Response<Post>
}

object PostApi {
    val retrofitService by lazy {
        retrofit.create(PostApiService::class.java)
    }
}