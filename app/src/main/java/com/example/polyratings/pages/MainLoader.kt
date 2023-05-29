package com.example.polyratings.pages

import android.graphics.drawable.Animatable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.polyratings.ui.theme.golden
import java.util.*

@Composable
fun MainLoader(
    modifier: Modifier = Modifier.fillMaxSize(),
    customGreeting: String = ""
) {
    val currentTime = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

    val greeting = when (currentTime) {
        in 0..11 -> "Good Morning"
        in 12..16 -> "Good Afternoon"
        else -> "Good Evening"
    }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(size = 64.dp),
            color = golden,
            strokeWidth = 6.dp
        )
        Spacer(modifier = Modifier.height(30.dp))

        Text(text = "${if(customGreeting.isNotEmpty())customGreeting else greeting}")
    }

}