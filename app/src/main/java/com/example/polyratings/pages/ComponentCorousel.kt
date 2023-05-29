package com.example.polyratings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.Professor
import com.example.polyratings.ui.theme.Green10
import com.example.polyratings.ui.theme.Green20
import com.example.polyratings.ui.theme.golden
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import kotlinx.coroutines.delay

const val AUTO_SLIDE_DURATION: Long = 4000
@OptIn(ExperimentalPagerApi::class)
@Composable
fun ComponentCrousel(
    modifier: Modifier = Modifier,
    professors: List<Professor> = listOf()
){
    Text(text = "Saurav")
    Card(
        modifier = Modifier.padding(16.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .border(
                width = 2.dp,
                color = golden, // golden color
                shape = RoundedCornerShape(10.dp) // 8.dp circular edges
            ),
        shape = RoundedCornerShape(10.dp)
        ,
    ) {
        AutoSlidingCarousel(
            itemsCount = 5,
            itemContent = { index ->
//                AsyncImage(
//                    model = ImageRequest.Builder(LocalContext.current)
//                        .data(images[index])
//                        .build(),
//                    contentDescription = null,
//                    contentScale = ContentScale.Crop,
//                    modifier = Modifier.height(200.dp)
//                )

                FeaturedChip(
                    professor = professors[index]
                )
            }
        )
    }
}


@OptIn(ExperimentalPagerApi::class)
@Composable
fun AutoSlidingCarousel(
    modifier: Modifier = Modifier,
    autoSlideDuration: Long = AUTO_SLIDE_DURATION,
    pagerState: PagerState = remember { PagerState() },
    itemsCount: Int,
    itemContent: @Composable (index: Int) -> Unit,
) {
    val isDragged = pagerState.interactionSource.collectIsDraggedAsState()

    LaunchedEffect(pagerState.currentPage) {
        delay(autoSlideDuration)
        pagerState.animateScrollToPage((pagerState.currentPage + 1) % itemsCount)
    }

    Box(
        modifier = modifier.fillMaxWidth(),
    ) {
        HorizontalPager(count = itemsCount, state = pagerState) { page ->
            itemContent(page)
        }

        // you can remove the surface in case you don't want
        // the transparant bacground
        Surface(
            modifier = Modifier
                .padding(bottom = 8.dp)
                .align(Alignment.BottomCenter),
            shape = CircleShape,
            color = Color.Black.copy(alpha = 0.5f)
        ) {
            DotsIndicator(
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
                totalDots = itemsCount,
                selectedIndex = if (isDragged.value) pagerState.currentPage else pagerState.targetPage,
                dotSize = 8.dp
            )
        }
    }
}


@Composable
fun DotsIndicator(
    modifier: Modifier = Modifier,
    totalDots: Int = 5,
    selectedIndex: Int = 0,
    selectedColor: Color = Green20,
    unSelectedColor: Color = Color.White,
    dotSize: Dp = 8.dp
) {
    LazyRow(
        modifier = modifier
            .wrapContentWidth()
            .wrapContentHeight()
    ) {
        items(totalDots) { index ->
            IndicatorDot(
                color = if (index == selectedIndex) selectedColor else unSelectedColor,
                size = dotSize
            )

            if (index != totalDots - 1) {
                Spacer(modifier = Modifier.padding(horizontal = 2.dp))
            }
        }
    }
}

@Composable
fun IndicatorDot(
    modifier: Modifier = Modifier,
    size: Dp,
    color: Color
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(color)
    )
}