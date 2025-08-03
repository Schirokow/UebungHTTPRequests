package com.example.bunghttprequests.data

import com.example.bunghttprequests.data.HttpService.client
import com.example.bunghttprequests.data.PostRepository.getPostById
import com.example.bunghttprequests.data.PostRepository.getPosts
import com.example.bunghttprequests.data.PostRepository.getPostsByUserId
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.put
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
                    println("suspend fun getPosts in PostRepository used")
                }.body<List<Post>>()
            }
        } catch (e: Exception){
            println("Fehler beim laden des Posts: ${e.message}")
            emptyList()
        }
    }

    suspend fun getPostsByUserId(userId: Int? = null): List<Post> {
        return try {
            withContext(Dispatchers.IO){
                client.get("https://jsonplaceholder.typicode.com/posts?userId=${userId}"){
                    contentType(ContentType.Application.Json)
                    println("suspend fun getPostsByUserId in PostRepository used")
                }.body<List<Post>>()
            }
        } catch (e: Exception){
            println("Fehler beim laden des Posts nach userId: ${e.message}")
            emptyList()
        }
    }


    suspend fun getPostById(id: Int?): Post? {
        return try {
            withContext(Dispatchers.IO){
                client.get("https://jsonplaceholder.typicode.com/posts/${id}"){
                    contentType(ContentType.Application.Json)
                    println("suspend fun getPostById in PostRepository used")
                }.body<Post>()
            }
        } catch (e: Exception){
            println("Fehler beim laden des Posts nach Id: ${e.message}")
            null
        }
    }

    suspend fun createPost(newPost: Post): Post?{
        return try {
            withContext(Dispatchers.IO){
                client.post("https://jsonplaceholder.typicode.com/posts"){
                    method = HttpMethod.Post
                    contentType(ContentType.Application.Json)
                    setBody(newPost)
                    println("suspend fun createPost in PostRepository used")
                }.body<Post>()
            }
        } catch (e: Exception){
            println("Fehler beim erstellen des Posts: ${e.message}")
            null
        }
    }

    suspend fun updatePost(post: Post): Post?{
        return try {
            withContext(Dispatchers.IO){
                client.put("https://jsonplaceholder.typicode.com/posts/${post.id}"){
                    contentType(ContentType.Application.Json)
                    setBody(post)
                    println("suspend fun updatePost in PostRepository used")
                }.body<Post>()
            }
        } catch (e: Exception){
            println("Fehler beim Aktualisieren des Posts: ${e.message}")
            null
        }
    }
}


fun postsDataFlow(): Flow<List<PostRepository.Post>> = flow {
    emit(getPosts())
}

fun getPostsByIdFlow(userId: Int?): Flow<List<PostRepository.Post>> = flow {
    emit(getPostsByUserId(userId))
}

fun getPostByIdFlow1(id: Int?): Flow<PostRepository.Post?> = flow {
    emit(getPostById(id))
}


interface PostsRepository{
    fun getPostsFlow(): Flow<List<PostRepository.Post>>
}

interface GetPostsByUserId{
    fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>>
}

interface GetPostById{
    fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?>
}

interface CreatePost{
    suspend fun createPost(newPost: PostRepository.Post): PostRepository.Post?
}

interface UpdatePost{
    suspend fun updatePost(post: PostRepository.Post): PostRepository.Post?
}

class PostsRepositoryImplFlow: PostsRepository{
    override fun getPostsFlow(): Flow<List<PostRepository.Post>> = postsDataFlow()


}

class GetPostsByUserIdImplFlow: GetPostsByUserId{
    override fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>> = getPostsByIdFlow(userId)


}

class GetPostByIdImplFlow: GetPostById{
    override fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?> = getPostByIdFlow1(id)
}

class CreatePostImpl: CreatePost{
    override suspend fun createPost(newPost: PostRepository.Post): PostRepository.Post? {
        return PostRepository.createPost(newPost)
    }
}

class UpdatePostImpl: UpdatePost{
    override suspend fun updatePost(post: PostRepository.Post): PostRepository.Post? {
        return PostRepository.updatePost(post)
    }
}