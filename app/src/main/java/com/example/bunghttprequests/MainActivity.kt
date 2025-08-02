package com.example.bunghttprequests

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
//import com.example.bunghttprequests.data.AppModule
import com.example.bunghttprequests.presentation.screens.PostsScreen
import com.example.bunghttprequests.ui.theme.ÜbungHTTPRequestsTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ÜbungHTTPRequestsTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    PostsScreen(
                        modifier = Modifier.padding(innerPadding),
                    )
                }
            }
        }
    }
}

