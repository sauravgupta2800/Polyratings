package com.example.polyratings.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SingleReviewCard(
    onFlag: ()-> Unit
){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(5.dp, spotColor = Color.Gray, clip = false)
            .background(Color.White)
            .clip(shape = RoundedCornerShape(10.dp))
            .padding(10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth(0.75f)
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,

                    ) {
                        Text(
                            text = "Grade Received: A",
                            fontSize = 13.sp
                        )
                        Text(
                            text = "Freshman",
                            fontSize = 13.sp,
                            modifier = Modifier.padding(start=20.dp))
                    }

                    Divider(
                        color = Color.LightGray,
                        modifier = Modifier.padding(vertical = 5.dp)
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Mar 2014",
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )

//                IconButton(
//                    onClick = { onFlag },
//                    modifier = Modifier.padding(0.dp),
//                ) {
//                    Icon(
//                        Icons.Outlined.Delete,
//                        contentDescription = "Dropdown Arrow",
//                        tint = Color.Gray,
//                        modifier = Modifier.size(24.dp)
//                    )
//                }
                Icon(
                    Icons.Outlined.Delete,
                    contentDescription = "Dropdown Arrow",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp)
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "A subject matter very clearly but if you took chemistry in high school you'll be fine. A Super Easy teacher. She doesn't teach the subject matter very clearly but if you took chemistry in high school you'll be fine.",
                    fontSize = 14.sp,
                )
            }
        }
    }
}