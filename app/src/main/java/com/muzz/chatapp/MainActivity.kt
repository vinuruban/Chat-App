package com.muzz.chatapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.muzz.chatapp.ui.ChatScreen
import com.muzz.chatapp.ui.ChatViewModel
import com.muzz.chatapp.ui.theme.ChatAppTheme
import com.muzz.chatapp.di.Di

class MainActivity : ComponentActivity() {
    private lateinit var chatViewModel: ChatViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Di.init(applicationContext)
        chatViewModel = ChatViewModel(Di.chatRepository)
        setContent {
            ChatAppTheme {
                ChatScreen(viewModel = chatViewModel)
            }
        }
    }
}
