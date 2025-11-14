package com.muzz.chatapp.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.muzz.chatapp.domain.model.Message
import com.muzz.chatapp.domain.model.User
import com.muzz.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: ChatRepository) : ViewModel() {

    private val user1 = User("1", "Mo Salah")
    private val user2 = User("2", "Sarah")

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.getMessages().collect { msgs ->
                val currentUser = when (val state = _uiState.value) {
                    is ChatUiState.Content -> state.activeUser
                    else -> user1
                }
                val otherUser = getOtherUser(currentUser)
                _uiState.value = ChatUiState.Content(
                    activeUser = currentUser,
                    otherUser = otherUser,
                    messages = msgs
                )
            }
        }
    }

    fun sendMessage(text: String) {
        val state = _uiState.value as? ChatUiState.Content ?: return
        val message = Message(text = text, senderId = state.activeUser.id)

        viewModelScope.launch {
            repository.sendMessage(message)
            delay(1000)
            repository.sendMessage(Message(text = fakeReply(), senderId = state.otherUser.id))
        }
    }

    fun switchUser() {
        val state = _uiState.value as? ChatUiState.Content ?: return
        val nextUser = state.otherUser
        val other = getOtherUser(nextUser)
        _uiState.value = state.copy(activeUser = nextUser, otherUser = other)
    }

    private fun getOtherUser(currentUser: User): User {
        return if (currentUser == user1) user2 else user1
    }

    private fun fakeReply(): String {
        return listOf(
            "Hi!",
            "That's great!",
            "Let's catch up!",
            "Interesting...",
            "I think so",
            "What are you up to today?",
            "Ok cool!",
            "Nothing much",
            "Wowsa sounds fun"
        ).random()
    }
}