package com.example.bunghttprequests.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bunghttprequests.card.PostCard

import com.example.bunghttprequests.data.PostRepository

import com.example.bunghttprequests.presentation.viewmodels.PostsViewModel
import com.example.bunghttprequests.ui.theme.ÜbungHTTPRequestsTheme
import kotlinx.coroutines.launch

@Composable
fun PostsScreen(modifier: Modifier = Modifier, viewModel: PostsViewModel) {


    val postsDataList by viewModel.postsData.collectAsState()
    val scope = rememberCoroutineScope()

    var eingabe by remember {
        mutableStateOf("")
    }

//    if (postsDataList.isEmpty()) {
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text("Keine Posts geladen", color = Color.White, fontSize = 20.sp)
//        }
//        return
//    }


    Column (
        modifier = Modifier.fillMaxSize()
    ){
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(top =60.dp),
            horizontalArrangement = Arrangement.SpaceEvenly,
            verticalAlignment = Alignment.CenterVertically
        ){
            Button(
                modifier = Modifier
                    .width(100.dp),
                onClick = {
                    viewModel.loadAllPosts()
//                    scope.launch {
//                        posts = getPosts()
//                    }
                }
            ) {
                Text("laden")
            }

            Button(
                modifier = Modifier
                    .width(100.dp),
                onClick = {
                    viewModel.deleteAllPosts()
//                    post = emptyList()
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Red
                )
            ) {

                Text("löschen")
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 50.dp)
        ){
            TextField(
                value = eingabe,
                singleLine = true,
                onValueChange = { text ->
                    eingabe = text
                }
            )
            Spacer(modifier = Modifier.width(10.dp))
            Icon(
                imageVector = Icons.Rounded.Send,
                contentDescription = "Add",
                tint = Color.Blue,
                modifier = Modifier
                    .size(34.dp)
                    .clickable{
                    Log.d("AddIcon", "createPost funktion mit: $eingabe ausgefürt")
                    if (eingabe.isNotBlank()) {
                        scope.launch {
//                            createPost(eingabe)
                        }
                        eingabe = ""
                   }
                    }
            )
//            Button(
//                onClick = {
//                    Log.d("AddButton", "createPost funktion mit: $eingabe ausgefürt")
//                    if (eingabe.isNotBlank()) {
//                        scope.launch {
////                            createPost(eingabe)
//                        }
//                        eingabe = ""
//                    }
//                }
//            ) {
//                Text(text = "Add")
//            }
        }
        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentPadding = PaddingValues(vertical = 16.dp)
        ){
            items(postsDataList){ post ->
                PostCard(
                    title = post.title,
                    body = post.body,
                    onClick = {}
                )
            }
        }
    }

}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun PostsScreenPreview() {
    ÜbungHTTPRequestsTheme {
//        PostsScreen(modifier = Modifier, viewModel = PostsViewModel())
    }
}