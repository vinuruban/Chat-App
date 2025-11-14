package com.muzz.chatapp.di

import android.content.Context
import com.muzz.chatapp.data.repository.ChatRepositoryImpl
import com.muzz.chatapp.data.local.ChatDatabase
import com.muzz.chatapp.domain.repository.ChatRepository

object Di {

    lateinit var chatRepository: ChatRepository
        private set

    fun init(context: Context) {
        val database = ChatDatabase.getInstance(context)
        chatRepository = ChatRepositoryImpl(database.chatDao())
    }
}