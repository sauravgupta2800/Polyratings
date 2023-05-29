package com.example.polyratings.pages

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.outlined.MoreVert
import androidx.compose.material.icons.outlined.Warning
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
import com.example.polyratings.data.Review
import com.example.polyratings.ui.theme.golden
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

@Composable
fun SingleReviewCard(
    review: Review,
    onFlag: ()-> Unit,
    modifer: Modifier = Modifier
){
    Box(
        modifier = modifer
            .fillMaxWidth()
            .shadow(5.dp, spotColor = golden, clip = false)
            .background(Color.White)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                width = 1.dp,
                color = golden, // golden color
                shape = RoundedCornerShape(8.dp) // 8.dp circular edges
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
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
                            text = "Grade Received: ${review.grade}",
                            fontSize = 13.sp
                        )
                        Text(
                            text = "${review.gradeLevel}",
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
                    text = formatDate(review.postDate),
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                )

                Icon(
                    Icons.Outlined.Warning,
                    contentDescription = "Dropdown Arrow",
                    tint = Color.Gray,
                    modifier = Modifier.size(20.dp).clickable {
                        onFlag()
                    }
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Text(
                    text = "${review.rating}",
                    fontSize = 14.sp,
                )
            }
        }
    }
}


fun formatDate(dateString: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
    val outputFormat = SimpleDateFormat("MMM, yyyy", Locale.ENGLISH)

    val date = inputFormat.parse(dateString)
    return outputFormat.format(date)
}