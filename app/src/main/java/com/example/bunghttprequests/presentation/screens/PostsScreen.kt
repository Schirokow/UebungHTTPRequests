package com.example.bunghttprequests.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.AddComment
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bunghttprequests.card.PostCard
import com.example.bunghttprequests.data.LocalStorageService
import org.koin.androidx.compose.koinViewModel
import com.example.bunghttprequests.data.PostRepository
import com.example.bunghttprequests.data.PostRepository.createPost

import com.example.bunghttprequests.presentation.viewmodels.PostsViewModel
import com.example.bunghttprequests.ui.theme.ÜbungHTTPRequestsTheme
import kotlinx.coroutines.launch
import java.nio.file.WatchEvent

@Composable
fun PostsScreen(modifier: Modifier = Modifier) {

    val TAG = "PostsScreen"

    val postsViewModel: PostsViewModel = koinViewModel<PostsViewModel>()
//    val postsDataList by postsViewModel.postsData.collectAsState()
    val postsDataList by postsViewModel.localStorageState.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // State, um ausgewählte Post zu speichern
    var selectedPost by remember { mutableStateOf<PostRepository.Post?>(null).also {
        Log.d(TAG, "Selected post state initialized")
    } }

//    val localPostStorage: LocalStorageService.LocalPostsStorage? = null
//    val postsDataList by viewModel.postsData.collectAsState()
//    val postsDataList by viewModel.localStorageState.collectAsState()
    val scope = rememberCoroutineScope()

    var eingabe by remember {
        mutableStateOf("")
    }

    var title by remember {
        mutableStateOf("")
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Alle Posts löschen?") },
            text = { Text("Möchtest du wirklich alle Posts löschen?") },
            confirmButton = {
                Button(
                    onClick = {
                        postsViewModel.deleteAllPosts()
                        showDeleteDialog = false
                        Log.d(TAG, "all posts deleted")
                    }
                ) {
                    Text("Löschen")
                }
            },
            dismissButton = {
                Button(onClick = { showDeleteDialog = false }) {
                    Text("Abbrechen")
                }
            }
        )
    }


//    if (postsDataList.isEmpty()) {
//        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//            Text("Keine Posts geladen", color = Color.White, fontSize = 20.sp)
//        }
//        return
//    }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(AccentColor)
    ){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(WindowInsets.systemBars.asPaddingValues())
                .background(brush = Brush.verticalGradient(colors = listOf(
                    TopLightBlue,
                    BottomDarkBlue
                )))
        ) {
            Column (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp)
            ){
                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = {
//                            viewModel.loadAllPosts()
                            postsViewModel.loadAllPosts()
                        }
                    ) {
                        Text("laden")
                    }

                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = {
                            showDeleteDialog = true
//                            viewModel.deleteAllPosts()
//                            postsViewModel.deleteAllPosts()
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
                OutlinedTextField(
                    value = title,
                    singleLine = true,
                    placeholder = {Text("Titel", color = Color.White)},
                    onValueChange = { text ->
                        title = text
                    },
                    modifier = Modifier.padding(start = 65.dp)
                )
                Spacer(modifier = Modifier.height(6.dp))
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
//                        .padding(start = 50.dp)
                ){

                    OutlinedTextField(
                        value = eingabe,
                        singleLine = true,
                        placeholder = {Text("Text eingeben", color = Color.White)},
                        onValueChange = { text ->
                            eingabe = text
                        },
                        modifier = Modifier.padding(start = 65.dp)
                    )
                    val newPost = PostRepository.Post(userId = 9, title = title, body = eingabe)
                    val localNewPost = LocalStorageService.LocalPostStorage(userId = 9, title = title, body = eingabe)

                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Rounded.Send,
                        contentDescription = "Add",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(34.dp)
                            .clickable{
                                Log.d("AddIcon", "createPost funktion mit: $newPost ausgefürt")
                                if (eingabe.isNotBlank()) {
//                                    scope.launch {
//                                        createPost(newPost)
                                        postsViewModel.createNewPost(newPost)
                                        postsViewModel.insertNewPost(localNewPost)
//                            localPostStorage?.insertNewPost(newPost)
//                                    }
                                    title = ""
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
                        Spacer(modifier = Modifier.height(60.dp))

                        Row (modifier = Modifier
                            .fillMaxWidth()
                        )
                        {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete",
                                tint = Color.Red,
                                modifier = Modifier
                                    .padding(end = 20.dp)
                                    .size(34.dp)
                                    .clickable{
                                        Log.d("DeleteIcon", "Post mit der Id:${post.id} gelöscht")
                                        if (post.userId == 9){
                                            scope.launch {
                                                postsViewModel.deleteLocalPostById(post.id)
                                            }
                                        } else{
                                            scope.launch {
                                                postsViewModel.deleteLocalPostById(post.id)
                                            }
                                        }

                                    }
                            )
//
                        }

                        PostCard(
                            title = post.title,
                            body = post.body,
                            onClick = {}
                        )

                    }
                }
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

val AccentColor = Color(0xFF29719E)
val BottomDarkBlue = Color(0xFF1A4D6C)
val TopLightBlue = Color(0xFF62A7C3)