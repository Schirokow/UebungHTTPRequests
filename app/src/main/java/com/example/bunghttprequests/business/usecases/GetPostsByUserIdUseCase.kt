package com.example.bunghttprequests.business.usecases

import com.example.bunghttprequests.data.GetPostsByUserId
import com.example.bunghttprequests.data.PostRepository
import kotlinx.coroutines.flow.Flow

class GetPostsByUserIdUseCase(private val posts: GetPostsByUserId) {
    fun getPostsByUserIdFlow(userId: Int?): Flow<List<PostRepository.Post>> {
        return posts.getPostsByUserIdFlow(userId)
    }
}