package com.example.bunghttprequests.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunghttprequests.business.event.LocalPostState
import com.example.bunghttprequests.business.event.PostEvent
import com.example.bunghttprequests.business.usecases.CreatePostUseCase
import com.example.bunghttprequests.business.usecases.GetPostsUseCase
import com.example.bunghttprequests.business.usecases.UpdatePostUseCase
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
    private val getPostsUseCase: GetPostsUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase
//    private val dao: PostsDao
): ViewModel() {
//    private val getPostsUseCase: GetPostsUseCase = GetPostsUseCase()

//    private val _postsData = MutableStateFlow<List<PostRepository.Post>>(emptyList())
//    val postsData: StateFlow<List<PostRepository.Post>> = _postsData.asStateFlow()

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
                localPostStorage.insertLocalPosts(posts)
//                _postsData.value = posts
            }

            // Sammle Daten von der lokalen Speicherung
            localPostStorage.getAllLocalPosts().collect { localPosts ->
                _localStorageState.value = localPosts
            }
        }

    }

    fun loadAllPosts(){
        viewModelScope.launch {
            try {
                getPostsUseCase.getPostsFlow().collect { posts ->
                    localPostStorage.insertLocalPosts(posts)
//                    localPostStorage.getAllLocalPosts().collect { localPosts ->
//                        _localStorageState.value = localPosts
//                    }
//                    _postsData.value = posts
                    Log.i("PostsViewModel", "All Posts loaded")
                }
            } catch (e: Exception){
                Log.e("PostsViewModel", "Error loading Posts: ${e.message}")
            }

        }
    }

    fun insertNewPost(post: LocalStorageService.LocalPostStorage) {
        viewModelScope.launch {
            try {
                localPostStorage.insertNewPost(post)
                Log.i("PostsViewModel", "New Post in LocalPostStorage inserted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error insert new Post in LocalPostStorage: ${e.message}")
            }
        }
    }

    fun createNewPost(newPost: PostRepository.Post) {
        viewModelScope.launch {
            try {
                createPostUseCase.createNewPost(newPost)
                Log.i("PostsViewModel", "createNewPost function used")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error createNewPost function: ${e.message}")
            }
        }
    }

    fun udatePost(post: PostRepository.Post) {
        viewModelScope.launch {
            try {
                updatePostUseCase.updatePost(post)
                Log.i("PostsViewModel", "updatePost function used")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error updatePost function: ${e.message}")
            }
        }

    }

    

    fun deleteAllPosts() {
        viewModelScope.launch {
            try {
                localPostStorage.deleteAllLocalPosts()
//                _postsData.value = emptyList()
                Log.i("PostsViewModel", "All Posts deleted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error deleting Posts: ${e.message}")
            }
        }
    }

    fun deleteLocalPostById(id: Int) {
        viewModelScope.launch {
            try {
                localPostStorage.deleteLocalPostById(id)
                Log.i("PostsViewModel", "Post by Id:$id deleted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error deleting Post by Id:$id : ${e.message}")
            }
        }
    }

}