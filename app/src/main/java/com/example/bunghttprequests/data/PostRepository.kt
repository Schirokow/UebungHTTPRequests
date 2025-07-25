package com.example.bunghttprequests.data

import com.example.bunghttprequests.data.HttpService.client
import com.example.bunghttprequests.data.PostRepository.getPosts
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.request
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
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
        return try {
            withContext(Dispatchers.IO){
                client.get("https://jsonplaceholder.typicode.com/posts"){
                    contentType(ContentType.Application.Json)
                }.body<List<Post>>()
            }
        } catch (e: Exception){
            println("Fehler beim laden des Posts: ${e.message}")
        } as List<Post>
    }

//    suspend fun getPostsByUserId(userId: Int): List<Post>{
//
//    }
//
//    suspend fun getPostById(id: Int): Post?{
//
//    }

    suspend fun createPost(newPost: Post): Post?{
        return try {
            withContext(Dispatchers.IO){
                client.request("https://jsonplaceholder.typicode.com/posts"){
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(newPost)
                }.body<Post>()
            }
        } catch (e: Exception){
            println("Fehler beim erstellen des Posts: ${e.message}")
            null
        }
    }

    suspend fun updatePost(post: Post){

    }
}


fun postsDataFlow(): Flow<List<PostRepository.Post>> = flow {
    emit(getPosts())
}

interface PostsRepository{
    fun getPostsFlow(): Flow<List<PostRepository.Post>>
}

class PostsRepositoryImplFlow: PostsRepository{
    override fun getPostsFlow(): Flow<List<PostRepository.Post>> = postsDataFlow()
}