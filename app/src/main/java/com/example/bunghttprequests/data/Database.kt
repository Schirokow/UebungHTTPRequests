package com.example.bunghttprequests.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.bunghttprequests.data.dao.PostsDao

@Database(entities = [LocalStorageService.LocalPostStorage::class], version = 1)

abstract class PostDatabase : RoomDatabase() {
    abstract fun postDao(): PostsDao
}

// Erklärung:
//@Database(entities = [PostStorage::class], version = 1): Definiert die Datenbank mit der PostStorage-Entity und einer Version (für spätere Migrationen).
//abstract fun postDao(): PostDao: Stellt das DAO bereit, um auf die Datenbank zuzugreifen.
//Die Klasse muss abstrakt sein und von RoomDatabase erben.
