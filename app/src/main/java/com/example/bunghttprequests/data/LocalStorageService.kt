package com.example.bunghttprequests.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bunghttprequests.data.dao.PostsDao
import kotlinx.coroutines.flow.Flow


object LocalStorageService {
    @Entity(tableName = "local_posts")
    data class LocalPostStorage(
        val userId: Int,
        @PrimaryKey(autoGenerate = true) val id: Int = 0,
        val title: String,
        val body: String
    )

    interface LocalPostsStorage {
        suspend fun insertLocalPosts(post: List<PostRepository.Post>)

//        suspend fun insertNewPost(post: PostRepository.Post)
        fun getAllLocalPosts(): Flow<List<LocalPostStorage>>
        suspend fun getLocalPostById(id: Int): LocalPostStorage?

        suspend fun deleteAllLocalPosts()
    }

    class PostsStorageImpl(private val dao: PostsDao) : LocalPostsStorage {
        override suspend fun insertLocalPosts(posts: List<PostRepository.Post>) {
            val postStorageEntities = posts.map { post ->
                LocalPostStorage(
                    // Wenn die ID von der API kommt, verwenden wir sie.
                    // Wenn nicht (z.B. bei einem neuen Post), lassen wir Room sie auto-generieren (id = 0).
                    userId = post.userId,
                    id = post.id?: 0,
                    title = post.title,
                    body = post.body
                )
            }
            dao.insert(postStorageEntities)
        }

//        override suspend fun insertNewPost(posts: PostRepository.Post) {
////            val newPostStorageEntities = posts.map { post ->
////                LocalPostStorage(
////                    // Wenn die ID von der API kommt, verwenden wir sie.
////                    // Wenn nicht (z.B. bei einem neuen Post), lassen wir Room sie auto-generieren (id = 0).
////                    userId = post.userId,
////                    id = post.id?: 0,
////                    title = post.title,
////                    body = post.body
////                )
////            }
//            dao.insertNewPost(PostRepository.Post)
//        }

        override fun getAllLocalPosts(): Flow<List<LocalPostStorage>> {
            return dao.getAllLocalPosts()
        }

        override suspend fun getLocalPostById(id: Int): LocalPostStorage? {
            return dao.getLocalPostById(id)
        }

        override suspend fun deleteAllLocalPosts() {
            dao.deleteAllLocalPosts()
        }
    }
}



//fun localStorageDataFlow(): Flow<List<LocalStorageService.LocalPostStorage>> = flow {
//    emit(LocalStorageService.PostsStorageImpl.getLocalPosts())
//}

