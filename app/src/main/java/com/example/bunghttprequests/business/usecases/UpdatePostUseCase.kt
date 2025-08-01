package com.example.bunghttprequests.business.usecases

import com.example.bunghttprequests.data.PostRepository
import com.example.bunghttprequests.data.UpdatePost

class UpdatePostUseCase(private val updatePost: UpdatePost) {
    suspend fun updatePost(post: PostRepository.Post): PostRepository.Post?{
        return updatePost.updatePost(post)
    }
}