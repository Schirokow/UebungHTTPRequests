package com.example.bunghttprequests.business.event

import com.example.bunghttprequests.data.LocalStorageService

sealed interface PostEvent {
    object SavePost: PostEvent
    data class SetTitle(val title: String): PostEvent
    data class SetBody(val body: String): PostEvent
    object ShowDialog: PostEvent
    object HideDialog: PostEvent
    data class DeletePost(val post: LocalStorageService.LocalPostStorage): PostEvent
}