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
import com.example.bunghttprequests.data.AppModule
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
//                    HomeScreen(
//                        modifier = Modifier.padding(innerPadding)
//                    )
                    // Hole den aktuellen Kontext
                    val context = LocalContext.current
                    // Erstelle die ViewModel Factory
                    val viewModelFactory = AppModule.providePostsViewModelFactory(context)
                    PostsScreen(
                        modifier = Modifier.padding(innerPadding),
                        viewModel = viewModel(factory = viewModelFactory)
                    )
                }
            }
        }
    }
}

//@Composable
//fun HomeScreen(modifier: Modifier = Modifier) {
//
//
//
//    val scope = rememberCoroutineScope()
//
//    var posts by remember {mutableStateOf<List<PostRepository.Post>>(emptyList())}
//    var eingabe by remember {
//        mutableStateOf("")
//    }
//
//
//    LaunchedEffect(Unit) {
//            posts = getPosts()
//    }
//
//
//    Column (
//        modifier = Modifier.fillMaxSize()
//    ){
//        Row (
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top =100.dp),
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            verticalAlignment = Alignment.CenterVertically
//        ){
//            Button(
//                onClick = {
//                    scope.launch {
//                        posts = getPosts()
//                    }
//                }
//            ) {
//                Text("laden")
//            }
//
//            Button(
//                onClick = {
//                    posts = emptyList()
//                }
//            ) {
//                Text("löschen")
//            }
//        }
//        Spacer(modifier = Modifier.height(30.dp))
//        Row (
//            horizontalArrangement = Arrangement.SpaceEvenly,
//            modifier = Modifier
//                .fillMaxWidth()
//        ){
//            TextField(
//                value = eingabe,
//                singleLine = true,
//                onValueChange = { text ->
//                    eingabe = text
//                }
//            )
//            Button(
//                onClick = {
//                    Log.d("AddButton", "createPost funktion mit: $eingabe ausgefürt")
//                    if (eingabe.isNotBlank()) {
//                        scope.launch {
//                            createPost(eingabe)
//                        }
//                        eingabe = ""
//                    }
//                }
//            ) {
//                Text(text = "Add")
//            }
//        }
//        LazyColumn (
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(16.dp),
//            contentPadding = PaddingValues(vertical = 16.dp)
//        ){
//            items(posts){ post ->
////                Text(post.title.take(10))
//                Text(post.body.take(10))
//            }
//        }
//    }
//
//}
//
//@Preview(showBackground = true)
//@Composable
//fun HomeScreenPreview() {
//    ÜbungHTTPRequestsTheme {
//        HomeScreen()
//    }
//}






//@Serializable
//data class Post(
//    val userId: Int,
//    val id: Int? = null,
//    val title: String,
//    val body: String
//)

//suspend fun getPosts(): Post{
//    return try {
//        withContext(Dispatchers.IO){
//            client.get("https://jsonplaceholder.typicode.com/posts").body<Post>()
//        }
//    } catch (e: Exception){
//        println("Fehler beim laden von Posts: ${e.message}")
//    } as Post
//}

//suspend fun getPosts(): List<PostRepository.Post> {
//    return client.get("https://jsonplaceholder.typicode.com/posts").body()
//}
//
//suspend fun createPost(newPost: String): PostRepository.Post?{
//    return try {
//        withContext(Dispatchers.IO){
//            client.post("https://jsonplaceholder.typicode.com/posts"){
//                contentType(ContentType.Application.Json)
//                setBody(newPost)
//            }.body< PostRepository.Post>()
//        }
//    } catch (e: Exception){
//        println("Fehler beim erstellen des Posts: ${e.message}")
//        null
//    }
//}