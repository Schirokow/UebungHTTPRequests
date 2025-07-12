package com.example.bunghttprequests.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunghttprequests.business.usecases.GetPostsUseCase
import com.example.bunghttprequests.data.LocalStorageService
import com.example.bunghttprequests.data.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(private val postStorage: LocalStorageService.LocalPostsStorage): ViewModel() {
    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase()

    private val _postsData = MutableStateFlow<List<PostRepository.Post>>(emptyList())
    val postsData: StateFlow<List<PostRepository.Post>> = _postsData.asStateFlow()

    init {
        viewModelScope.launch {
            // Posts laden
            getPostsUseCase.getPostsFlow().collect { posts ->
                _postsData.value = posts
            }
        }

    }

    fun loadAllPosts(){
        viewModelScope.launch {
            try {
                getPostsUseCase.getPostsFlow().collect { posts ->
                    postStorage.insertLocalPosts(posts)
                    _postsData.value = posts
                    Log.i("PostsViewModel", "All Posts loaded")
                }
            } catch (e: Exception){
                Log.e("PostsViewModel", "Error loading Posts: ${e.message}")
            }

        }
    }

    fun deleteAllPosts() {
        viewModelScope.launch {
            try {
                postStorage.deleteAllLocalPosts()
                _postsData.value = emptyList()
                Log.i("PostsViewModel", "All Posts deleted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error deleting Posts: ${e.message}")
            }
        }
    }

}