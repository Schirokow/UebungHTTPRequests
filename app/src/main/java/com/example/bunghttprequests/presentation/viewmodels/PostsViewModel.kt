package com.example.bunghttprequests.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunghttprequests.business.event.LocalPostState
import com.example.bunghttprequests.business.event.PostEvent
import com.example.bunghttprequests.business.usecases.GetPostsUseCase
import com.example.bunghttprequests.data.LocalStorageService
import com.example.bunghttprequests.data.PostRepository
import com.example.bunghttprequests.data.dao.PostsDao
import io.ktor.util.Hash.combine
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class PostsViewModel(
    private val localPostStorage: LocalStorageService.LocalPostsStorage,
    private val getPostsUseCase: GetPostsUseCase
//    private val dao: PostsDao
): ViewModel() {
//    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase()

    private val _postsData = MutableStateFlow<List<PostRepository.Post>>(emptyList())
    val postsData: StateFlow<List<PostRepository.Post>> = _postsData.asStateFlow()

    private val _localStorageState = MutableStateFlow<List<LocalStorageService.LocalPostStorage>>(emptyList())
    val localStorageState: StateFlow<List<LocalStorageService.LocalPostStorage>> = _localStorageState.asStateFlow()

//

//    fun onEvent(event: PostEvent){
//        when(event){
//            is PostEvent.DeletePost -> {
//                viewModelScope.launch {
//                    dao.deleteAllLocalPosts()
//                }
//
//            }
//            PostEvent.HideDialog -> {
//                _localStorageState.update { it.copy(
//                    isAddingPost = false
//                ) }
//            }
//            PostEvent.SavePost -> {
//
//            }
//            is PostEvent.SetTitle -> {
//                _localStorageState.update {it.copy(
//                    title = event.title
//                )
//                }
//            }
//            is PostEvent.SetBody -> {
//                _localStorageState.update { it.copy(
//                    body = event.body
//                ) }
//            }
//            PostEvent.ShowDialog -> {
//                _localStorageState.update { it.copy(
//                    isAddingPost = true
//                ) }
//            }
//        }
//    }

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
                    localPostStorage.insertLocalPosts(posts)
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
                localPostStorage.deleteAllLocalPosts()
                _postsData.value = emptyList()
                Log.i("PostsViewModel", "All Posts deleted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error deleting Posts: ${e.message}")
            }
        }
    }

}