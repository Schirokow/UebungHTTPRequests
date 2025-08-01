package com.example.bunghttprequests.business.usecases

import com.example.bunghttprequests.data.CreatePost
import com.example.bunghttprequests.data.PostRepository

class CreatePostUseCase(private val createPost: CreatePost) {

    suspend fun createNewPost(newPost: PostRepository.Post): PostRepository.Post?{
        return createPost.createPost(newPost)
    }
}