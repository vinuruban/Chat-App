package com.muzz.chatapp.data.repository

import com.muzz.chatapp.data.local.ChatDao
import com.muzz.chatapp.data.local.MessageEntity
import com.muzz.chatapp.domain.model.Message
import com.muzz.chatapp.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ChatRepositoryImpl(private val dao: ChatDao) : ChatRepository {
    override fun getMessages(): Flow<List<Message>> {
        return dao.getMessages().map { list ->
            list.map {
                Message(
                    id = it.id,
                    text = it.text,
                    timestamp = it.timestamp,
                    senderId = it.senderId
                )
            }
        }
    }

    override suspend fun sendMessage(message: Message) {
        val entity = MessageEntity(
            id = message.id,
            text = message.text,
            timestamp = message.timestamp,
            senderId = message.senderId
        )
        dao.insertMessage(entity)
    }
}