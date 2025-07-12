package com.example.bunghttprequests.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bunghttprequests.data.PostRepository
import com.example.bunghttprequests.data.PostStorage
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: List<PostStorage>) // Fügt Post ein


    @Query("SELECT * FROM posts")
    fun getAllPosts(): Flow<List<PostStorage>> // Gibt alle Posts als Flow zurück

    @Query("SELECT * FROM posts WHERE id = :id")
    suspend fun getPostById(id: Int): PostStorage? // Gibt ein Post nach ID zurück

    @Query("DELETE FROM posts")
    suspend fun deleteAllPosts() // Methode zum Löschen aller Posts

}