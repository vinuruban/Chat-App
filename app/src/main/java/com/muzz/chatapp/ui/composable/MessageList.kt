package com.muzz.chatapp.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.muzz.chatapp.domain.model.Message
import com.muzz.chatapp.ui.theme.ChatTextSecondary
import com.muzz.chatapp.utils.formatToTimestamp
import com.muzz.chatapp.utils.isOverAnHour
import com.muzz.chatapp.utils.isWithin20Seconds

@Composable
fun MessageList(
    modifier: Modifier,
    messages: List<Message>,
    currentUserId: String
) {
    val listState = rememberLazyListState()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.lastIndex)
        }
    }

    LazyColumn(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        state = listState
    ) {
        itemsIndexed(messages) { index, message ->
            val prev = messages.getOrNull(index - 1)

            if (prev == null || isOverAnHour(prev.timestamp, message.timestamp)) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = formatToTimestamp(message.timestamp),
                        style = MaterialTheme.typography.labelSmall,
                        color = ChatTextSecondary,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                }
            }

            if (prev != null && prev.senderId == message.senderId &&
                isWithin20Seconds(prev.timestamp, message.timestamp)) {
                Spacer(modifier = Modifier.height(8.dp))
            }

            MessageBubble(
                message = message,
                isCurrentUser = message.senderId == currentUserId
            )
        }
    }
}