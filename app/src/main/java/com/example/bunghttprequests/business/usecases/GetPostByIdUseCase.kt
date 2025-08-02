package com.example.bunghttprequests.business.usecases

import com.example.bunghttprequests.data.GetPostById
import com.example.bunghttprequests.data.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostByIdUseCase(private val post: GetPostById) {
     fun getPostByIdFlow(id: Int?): Flow<PostRepository.Post?> {
        return post.getPostByIdFlow(id)
    }
}