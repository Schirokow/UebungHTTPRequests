package com.example.bunghttprequests.data

import android.content.Context
import androidx.room.Room
import com.example.bunghttprequests.data.dao.PostsDao

object DatabaseProvider {
    @Volatile
    private var POST_INSTANCE: PostDatabase? = null

    fun getPostDatabase(context: Context): PostDatabase {
        return POST_INSTANCE ?: synchronized(this) {
            val instance = Room.databaseBuilder(
                context.applicationContext,
                PostDatabase::class.java,
                "festival_database"
            )
                .build()
            POST_INSTANCE = instance
            instance
        }
    }

    fun providePostDao(context: Context): PostsDao {
        return getPostDatabase(context).postDao()
    }

}

//Erklärung:
//@Volatile und synchronized: Stellen sicher, dass die Datenbank thread-sicher initialisiert wird.
//Die Datenbank wird nur einmal erstellt und wiederverwendet.
//Methode getPostDatabase für die PostDatabase.
//providePostDao für einfachen Zugriff auf PostsDao.
