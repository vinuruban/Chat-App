package com.muzz.chatapp.ui

import com.muzz.chatapp.domain.model.Message
import com.muzz.chatapp.domain.model.User

sealed class ChatUiState {
    data object Loading : ChatUiState()
    data class Content(
        val activeUser: User,
        val otherUser: User,
        val messages: List<Message>
    ) : ChatUiState()
    data class Error(val message: String) : ChatUiState()
}