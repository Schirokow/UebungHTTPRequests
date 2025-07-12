package com.example.bunghttprequests.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.bunghttprequests.data.dao.PostsDao
import kotlinx.coroutines.flow.Flow

@Entity(tableName = "posts")
data class PostStorage(
    val userId: Int,
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val title: String,
    val body: String
)

interface PostsStorage {
    suspend fun insertPosts(post: List<PostRepository.Post>)
    fun getPosts(): Flow<List<PostStorage>>
    suspend fun getPostById(id: Int): PostStorage?

    suspend fun deleteAllPosts()
}

class PostsStorageImpl(private val dao: PostsDao) : PostsStorage {
    override suspend fun insertPosts(posts: List<PostRepository.Post>) {
        val postStorageEntities = posts.map { post ->
            PostStorage(
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

    override fun getPosts(): Flow<List<PostStorage>> {
        return dao.getAllPosts()
    }

    override suspend fun getPostById(id: Int): PostStorage? {
        return dao.getPostById(id)
    }

    override suspend fun deleteAllPosts() {
        dao.deleteAllPosts()
    }
}
