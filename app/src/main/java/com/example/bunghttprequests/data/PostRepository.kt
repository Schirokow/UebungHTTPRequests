package com.example.bunghttprequests.data

import com.example.bunghttprequests.data.HttpService.client
import com.example.bunghttprequests.data.PostRepository.getPosts
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import kotlinx.serialization.Serializable

object PostRepository {
    @Serializable
    data class Post(
        val userId: Int,
        val id: Int? = null,
        val title: String,
        val body: String
    )

    suspend fun getPosts(): List<Post> {
        return client.get("https://jsonplaceholder.typicode.com/posts").body()
    }

    suspend fun createPost(newPost: String): Post?{
        return try {
            withContext(Dispatchers.IO){
                client.post("https://jsonplaceholder.typicode.com/posts"){
                    contentType(ContentType.Application.Json)
                    setBody(newPost)
                }.body<Post>()
            }
        } catch (e: Exception){
            println("Fehler beim erstellen des Posts: ${e.message}")
            null
        }
    }
}


fun postsDataFlow(): Flow<List<PostRepository.Post>> = flow {
    emit(getPosts())
}

class PostsRepositoryImplFlow{
    fun getPostsFlow(): Flow<List<PostRepository.Post>> = postsDataFlow()
}