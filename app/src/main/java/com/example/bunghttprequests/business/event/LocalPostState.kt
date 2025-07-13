package com.example.bunghttprequests.business.event

import com.example.bunghttprequests.data.LocalStorageService

data class LocalPostState(
    val localPosts: List<LocalStorageService.LocalPostStorage> = emptyList(),
    val title: String = "",
    val body: String = "",
    val isAddingPost: Boolean = false
)
