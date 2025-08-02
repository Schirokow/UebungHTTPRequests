package com.example.bunghttprequests.presentation.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bunghttprequests.business.usecases.CreatePostUseCase
import com.example.bunghttprequests.business.usecases.GetPostByIdUseCase
import com.example.bunghttprequests.business.usecases.GetPostsByUserIdUseCase
import com.example.bunghttprequests.business.usecases.GetPostsUseCase
import com.example.bunghttprequests.business.usecases.UpdatePostUseCase
import com.example.bunghttprequests.data.LocalStorageService
import com.example.bunghttprequests.data.PostRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PostsViewModel(
    private val localPostStorage: LocalStorageService.LocalPostsStorage,
    private val getPostsUseCase: GetPostsUseCase,
    private val createPostUseCase: CreatePostUseCase,
    private val updatePostUseCase: UpdatePostUseCase,
    private val getPostsByUserIdUseCase: GetPostsByUserIdUseCase,
    private val getPostByIdUseCase: GetPostByIdUseCase
): ViewModel() {

//    private val _postsData = MutableStateFlow<List<PostRepository.Post>>(emptyList())
//    val postsData: StateFlow<List<PostRepository.Post>> = _postsData.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _localStorageState = MutableStateFlow<List<LocalStorageService.LocalPostStorage>>(emptyList())
    val localStorageState: StateFlow<List<LocalStorageService.LocalPostStorage>> = _localStorageState.asStateFlow()


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
            _isLoading.value = true
            try {
                getPostsUseCase.getPostsFlow().collect { posts ->
                    localPostStorage.insertLocalPosts(posts)
//                    localPostStorage.getAllLocalPosts().collect { localPosts ->
//                        _localStorageState.value = localPosts
//                    }
                    Log.i("PostsViewModel", "All Posts loaded")
                }
            } catch (e: Exception){
                Log.e("PostsViewModel", "Error loading Posts: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun loadPostsByUserId(userId: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getPostsByUserIdUseCase.getPostsByUserIdFlow(userId).collect { posts ->
                    localPostStorage.insertLocalPosts(posts)
                    Log.i("PostsViewModel", "All Posts by userId loaded")
                }
            } catch (e: Exception){
                Log.e("PostsViewModel", "Error loading Posts by userId: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun loadPostById(id: Int?) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                getPostByIdUseCase.getPostByIdFlow(id).collect { post ->
                    val localPost = LocalStorageService.LocalPostStorage(
                        id = post?.id ?: 0,
                        userId = post?.userId ?: 0,
                        title = post?.title ?: "",
                        body = post?.body ?: ""
                    )
                    localPostStorage.insertNewPost(localPost)
                }
                    Log.i("PostsViewModel", "Post by id is loaded")

            } catch (e: Exception){
                Log.e("PostsViewModel", "Error loading Posts by userId: ${e.message}")
            } finally {
                _isLoading.value = false
            }

        }
    }

    fun insertNewPost(post: LocalStorageService.LocalPostStorage) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                localPostStorage.insertNewPost(post)
                Log.i("PostsViewModel", "New Post in LocalPostStorage inserted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error insert new Post in LocalPostStorage: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun createNewPost(newPost: PostRepository.Post) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                createPostUseCase.createNewPost(newPost)
                Log.i("PostsViewModel", "createNewPost function used")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error createNewPost function: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun udatePost(post: PostRepository.Post) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                updatePostUseCase.updatePost(post)
                Log.i("PostsViewModel", "updatePost function used")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error updatePost function: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }

    }

    

    fun deleteAllPosts() {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                localPostStorage.deleteAllLocalPosts()
                Log.i("PostsViewModel", "All Posts deleted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error deleting Posts: ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun deleteLocalPostById(id: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                localPostStorage.deleteLocalPostById(id)
                Log.i("PostsViewModel", "Post by Id:$id deleted")
            } catch (e: Exception) {
                Log.e("PostsViewModel", "Error deleting Post by Id:$id : ${e.message}")
            } finally {
                _isLoading.value = false
            }
        }
    }

}