package com.example.bunghttprequests.presentation.screens

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Send
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bunghttprequests.card.PostCard
import com.example.bunghttprequests.data.LocalStorageService
import org.koin.androidx.compose.koinViewModel
import com.example.bunghttprequests.data.PostRepository
import androidx.compose.ui.text.TextStyle
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.unit.dp


import com.example.bunghttprequests.presentation.viewmodels.PostsViewModel
import com.example.bunghttprequests.ui.theme.ÜbungHTTPRequestsTheme
import kotlinx.coroutines.launch


@Composable
fun PostsScreen(modifier: Modifier = Modifier) {

    val TAG = "PostsScreen"

    val postsViewModel: PostsViewModel = koinViewModel<PostsViewModel>()
//    val postsDataList by postsViewModel.postsData.collectAsState()
    val postsDataList by postsViewModel.localStorageState.collectAsState()
    val isLoading by postsViewModel.isLoading.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }

    // State, um ausgewählte Post zu speichern
    var selectedPost by remember { mutableStateOf<LocalStorageService.LocalPostStorage?>(null).also {
        Log.d(TAG, "Selected post state initialized")
    } }

    val scope = rememberCoroutineScope()

    var eingabe by remember {
        mutableStateOf("")
    }

    var title by remember {
        mutableStateOf("")
    }

    val userId: Int? = 3
    val postId: Int? = 33
    val localUserId = 11

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
//                            postsViewModel.loadAllPosts()
                            postsViewModel.loadPostsByUserId(userId)
                        }
                    ) {
                        Text("userId")
                    }

                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = {
                            postsViewModel.loadPostById(postId)
                        }
                    ) {
                        Text("postId")
                    }

                    Button(
                        modifier = Modifier
                            .width(100.dp),
                        onClick = {
                            showDeleteDialog = true
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
                    textStyle = TextStyle(color = Color.White),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = Color.Green,
                        unfocusedBorderColor = Color.Blue
                    ),
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
                ){

                    OutlinedTextField(
                        value = eingabe,
                        singleLine = true,
                        placeholder = {Text("Text eingeben", color = Color.White)},
                        textStyle = TextStyle(color = Color.White),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Green,
                            unfocusedBorderColor = Color.Blue
                        ),
                        onValueChange = { text ->
                            eingabe = text
                        },
                        modifier = Modifier.padding(start = 65.dp)
                    )
                    val newPost = PostRepository.Post(userId = localUserId, title = title, body = eingabe)
                    val localNewPost = LocalStorageService.LocalPostStorage(userId = localUserId, title = title, body = eingabe)

                    Spacer(modifier = Modifier.width(10.dp))
                    Icon(
                        imageVector = Icons.Rounded.Send,
                        contentDescription = "Add",
                        tint = Color.Blue,
                        modifier = Modifier
                            .size(34.dp)
                            .clickable{
                                Log.d("AddIcon", "createPost funktion mit: $newPost ausgefürt")
                                if (eingabe.isNotBlank() || title.isNotBlank()) {
                                    postsViewModel.createNewPost(newPost)
                                    postsViewModel.insertNewPost(localNewPost)
                                    title = ""
                                    eingabe = ""
                                }
                            }
                    )
                }

                when {
                    isLoading -> {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(color = Color.White)
                            Text(
                                "Lade...",
                                color = Color.White,
                                fontSize = 28.sp,
                                modifier = Modifier.padding(top = 70.dp)
                            )
                        }
                    }
                    else -> {
                        LazyColumn (
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp),
                            contentPadding = PaddingValues(vertical = 16.dp)
                        ){
                            items(postsDataList){ post ->
                                PostCard(
                                    userId = post.userId,
                                    title = post.title,
                                    body = post.body,
                                    onClick = {
                                        Log.d(TAG, "PostCard clicked - id: ${post.id}, title: ${post.title.take(15)}...")
                                        selectedPost = post
                                    }
                                )

                            }
                        }
                    }
                }
            }
        }
    }

    val animateScale by animateFloatAsState(
        targetValue = if (selectedPost != null) 1f else 0.5f,
        animationSpec = tween(durationMillis = 400)
    )

    // Overlay für vergrößertes Bild, wenn selectedPost nicht null ist
    selectedPost?.let { post ->
        Log.d(TAG, "Showing detail overlay for post id: ${post.id}")

        Surface(
            color = BackgroundColor.copy(alpha = 0.9f), // Farbe von Hintergrund
            modifier = Modifier.fillMaxSize(),
            onClick = { /*selectedPost = null */} // Klick außerhalb schließt das Overlay
        ) {
            Box (
                modifier = Modifier.fillMaxSize()
            ){
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(top = 130.dp)
                        .align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ){

                    OutlinedTextField(
                        value = title,
                        singleLine = true,
                        placeholder = {Text("Titel bearbeiten", color = Color.White)},
                        textStyle = TextStyle(color = Color.White),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = Color.Green,
                            unfocusedBorderColor = Color.Blue
                        ),
                        onValueChange = { text ->
                            title = text
                        }
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Row (
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                    ){

                        OutlinedTextField(
                            value = eingabe,
                            singleLine = true,
                            placeholder = {Text("Text bearbeiten", color = Color.White)},
                            textStyle = TextStyle(color = Color.White),
                            colors = OutlinedTextFieldDefaults.colors(
                                focusedBorderColor = Color.Green,
                                unfocusedBorderColor = Color.Blue
                            ),
                            onValueChange = { text ->
                                eingabe = text
                            },
                            modifier = Modifier.padding(start = 65.dp)
                        )
                        val udatePost = PostRepository.Post(userId = post.userId, id = post.id, title = title, body = eingabe)
                        val localUdatePost = LocalStorageService.LocalPostStorage(userId = post.userId, id = post.id, title = title, body = eingabe)

                        Spacer(modifier = Modifier.width(10.dp))
                        Icon(
                            imageVector = Icons.Rounded.Send,
                            contentDescription = "Add",
                            tint = Color.Blue,
                            modifier = Modifier
                                .size(34.dp)
                                .clickable{
                                    Log.d("AddIcon", "createPost funktion mit: $udatePost ausgefürt")
                                    if (eingabe.isNotBlank() || title.isNotBlank()) {
                                        postsViewModel.udatePost(udatePost)
                                        postsViewModel.insertNewPost(localUdatePost)
                                        title = ""
                                        eingabe = ""
                                        selectedPost = null
                                    }
                                }
                        )
                    }

                Spacer(modifier = Modifier.height(50.dp))

                    PostCard(
                        userId = post.userId,
                        title = post.title,
                        body = post.body,
                        onClick = {},
                        modifier = Modifier
                            .graphicsLayer(scaleX = animateScale, scaleY = animateScale)
                            .fillMaxWidth(0.9f)
//                            .fillMaxHeight(0.5f)
                            .height(300.dp)
                    )
                }

                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "Close",
                    tint = Color.White,
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .padding(start = 24.dp, top = 42.dp)
                        .size(34.dp)
                        .clickable{
                            Log.d(TAG, "Close button clicked, hiding detail view")
                            selectedPost = null
                        }
                )

                Icon(
                    imageVector = Icons.Rounded.Delete,
                    contentDescription = "Delete",
                    tint = Color.Red,
                    modifier = Modifier
                        .align(alignment = Alignment.TopEnd)
                        .padding(end = 24.dp, top = 42.dp)
                        .size(34.dp)
                        .clickable{
                            Log.d("DeleteIcon", "Post mit der Id:${post.id} gelöscht")
                            postsViewModel.deleteLocalPostById(post.id)
                            selectedPost = null
                        }
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

val AccentColor = Color(0xFF29719E)
val BottomDarkBlue = Color(0xFF1A4D6C)
val TopLightBlue = Color(0xFF62A7C3)
val BackgroundColor = Color(0xFF20587B)