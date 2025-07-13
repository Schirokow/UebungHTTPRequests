package com.example.bunghttprequests.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.bunghttprequests.data.LocalStorageService
import com.example.bunghttprequests.data.PostRepository
import kotlinx.coroutines.flow.Flow

@Dao
interface PostsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(post: List<LocalStorageService.LocalPostStorage>) // Fügt Post ein

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertNewPost(post: PostRepository.Post.Companion)


    @Query("SELECT * FROM local_posts")
    fun getAllLocalPosts(): Flow<List<LocalStorageService.LocalPostStorage>> // Gibt alle Posts als Flow zurück

    @Query("SELECT * FROM local_posts WHERE id = :id")
    suspend fun getLocalPostById(id: Int): LocalStorageService.LocalPostStorage? // Gibt ein Post nach ID zurück

    @Query("DELETE FROM local_posts")
    suspend fun deleteAllLocalPosts() // Methode zum Löschen aller Posts

}