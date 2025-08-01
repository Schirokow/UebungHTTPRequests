package com.example.bunghttprequests.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Preview //(showBackground = true)
@Composable
fun PreviewNewEventCard(){

    PostCard(
        userId = 1,
        title = "Test Post",
        body = "ein Post zum testen!",
        onClick = {}
    )
}

@Composable
fun PostCard(
    userId: Int,
    title: String,
    body: String,
    modifier : Modifier = Modifier,
    onClick: () -> Unit,
){
    Card(
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(12.dp),
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable(onClick = onClick)
    ){
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.LightGray)

            ){
                Column(
                    modifier = Modifier.padding(8.dp)
                ){
                    Text(
                        text = "userId:$userId",
                        color = Color.Red,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = title,
                        color = Color.Blue,
                        fontSize = 25.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = body,
                        color = Color.Black,
                        fontSize = 22.sp
                    )
                    
                }

            }
        }
    }
