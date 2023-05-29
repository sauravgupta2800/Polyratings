package com.example.polyratings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.polyratings.ui.theme.Grey100
import com.example.polyratings.ui.theme.Grey30
import com.example.polyratings.ui.theme.Grey90
import com.example.polyratings.ui.theme.golden
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun RatingBar(
    iconModified: Modifier = Modifier,
    modifier: Modifier = Modifier,
    rating: Double = 0.0,
    stars: Int = 4,
    starsColor: Color = golden,
) {
    val filledStars = floor(rating).toInt()
    val unfilledStars = (stars - ceil(rating)).toInt()
    val halfStar = !(rating.rem(1).equals(0.0))

    val value = rating.rem(1) // Example percentage value, range: 0.0f to 1.0f

    val filledPercentage =  (value * 100).coerceIn(0.0, 1.0)


    Row(modifier = modifier) {
        repeat(filledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = starsColor,
                modifier = iconModified
            )
        }
        if (halfStar) {
            Icon(
                imageVector = Icons.Default.Star,
                contentDescription = null,
                tint = starsColor.copy(alpha = (starsColor.alpha * value).toFloat()),
                modifier = iconModified
            )
        }
        repeat(unfilledStars) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                tint = Grey100,
                modifier = iconModified
            )
        }
    }
}

@Preview
@Composable
fun RatingPreview() {
    RatingBar(rating = 2.1, iconModified = Modifier.size(20.dp))
}