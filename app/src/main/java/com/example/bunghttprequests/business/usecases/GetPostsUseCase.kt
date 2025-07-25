package com.example.bunghttprequests.business.usecases

import com.example.bunghttprequests.data.PostRepository
import com.example.bunghttprequests.data.PostsRepository
import com.example.bunghttprequests.data.PostsRepositoryImplFlow
import kotlinx.coroutines.flow.Flow

class GetPostsUseCase(private val posts: PostsRepository) {
//    private val posts = PostsRepositoryImplFlow()
    fun getPostsFlow(): Flow<List<PostRepository.Post>> {
        return posts.getPostsFlow()
    }
}

