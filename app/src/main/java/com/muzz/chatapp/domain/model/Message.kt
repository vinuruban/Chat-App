package com.muzz.chatapp.domain.model

data class Message(
    val id: String = System.currentTimeMillis().toString(),
    val text: String,
    val timestamp: Long = System.currentTimeMillis(),
    val senderId: String
)