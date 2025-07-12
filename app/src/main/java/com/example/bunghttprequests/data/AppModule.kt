package com.example.bunghttprequests.data

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bunghttprequests.presentation.viewmodels.PostsViewModel

object AppModule {
    private fun providePostStorage(context: Context): PostsStorage {
        return PostsStorageImpl(DatabaseProvider.providePostDao(context))
    }

    fun providePostsViewModelFactory(context: Context): ViewModelProvider.Factory {
        return object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                if (modelClass.isAssignableFrom(PostsViewModel::class.java)) {
                    @Suppress("UNCHECKED_CAST")
                    return PostsViewModel(providePostStorage(context)) as T
                }
                throw IllegalArgumentException("Unknown ViewModel class")
            }
        }
    }
}

//Erklärung:
//provideFestivalRepository erstellt das FestivalRepository mit einem FestivalDao aus der FestivalDatabase.
//Die Factories (provideHomeViewModelFactory und provideDetailViewModelFactory) benötigen den Context, um die Datenbank zu initialisieren.
//Die Factories werden verwendet, um die ViewModels mit dem korrekten FestivalRepository zu instanziieren.