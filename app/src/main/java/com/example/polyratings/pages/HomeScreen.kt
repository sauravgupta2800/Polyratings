package com.example.polyratings.pages

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.material3.ButtonDefaults.outlinedShape
import androidx.compose.material3.TextFieldDefaults.outlinedShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.Professor
import com.example.polyratings.ui.theme.Green10
import com.example.polyratings.ui.theme.Green20
import com.example.polyratings.ui.theme.golden


@ExperimentalMaterial3Api
@Composable
fun HomeScreen(
    viewModel: PolyratingViewModel = viewModel(),
    onSearchClick: () -> Unit
//    modifier: Modifier = Modifier,

) {
    val openProfessorDetailsDialog = remember { mutableStateOf(false) }
    val openProfessorDetailsId = remember { mutableStateOf("") }
    val uiState by viewModel.uiState.collectAsState()
    val topProfessors = uiState.professorList
        .filter { it.numEvals > 10 }
        .sortedByDescending { it.overallRating }
        .take(6)

    if(openProfessorDetailsDialog.value){
        ProfessorDetailsDialog(
            id = openProfessorDetailsId.value,
            onClose = {
                openProfessorDetailsId.value = ""
                openProfessorDetailsDialog.value = false
            },
        )
    }

    Image(
        painter = rememberAsyncImagePainter("https://polyratings.dev/assets/home-header.6c92036b.webp"),
        contentDescription = null,
        modifier = Modifier.size(2800.dp),
        contentScale = ContentScale.Crop,
    )


    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Text(
            text = "Polyratings",
            style = MaterialTheme.typography.titleLarge.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 50.sp,
                shadow = Shadow(
                    color = Color.DarkGray,
                    offset = Offset(2.0f, 5.0f),
                    blurRadius = 2f
                )
            ),

        )
        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = "${uiState.searchKey}",
            onValueChange = { viewModel.handleSearchKeyChange(it) },
            singleLine = true,
            placeholder = { Text("Enter a Name") },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.onSecondaryContainer ,
                textColor = MaterialTheme.colorScheme.onSecondaryContainer,
            ),
            trailingIcon = {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon"
                )
            },
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier
                .border(1.dp,MaterialTheme.colorScheme.onSecondaryContainer, RoundedCornerShape(8.dp))
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = { onSearchClick() },
            modifier = Modifier.padding(0.dp),
            elevation = ButtonDefaults.buttonElevation(8.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            shape = RoundedCornerShape(4.dp)
        ) {
            Text(
                text = "Search",
                modifier = Modifier.padding(6.dp),
                fontSize = 18.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier
                .padding(0.dp)
                .offset(y = 0.dp)

        ) {
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                tint=golden
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .offset(y = -7.5.dp),
                tint=golden
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)
                    .offset(y = -12.dp),
                tint = Color(0xFFD4AF37),

            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .offset(y = -7.5.dp),
                tint=golden
            )
            Icon(
                imageVector = Icons.Filled.Star,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp),
                tint=golden
            )
        }

        Column(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .background(Green10, shape = RoundedCornerShape(8.dp))
                .clip(shape = RoundedCornerShape(8.dp))
                .border(
                    width = 2.dp,
                    color = golden, // golden color
                    shape = RoundedCornerShape(8.dp) // 8.dp circular edges
                )
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Featured Professor",
                style = MaterialTheme.typography.titleLarge,
                color = Color.White
            )
            Spacer(modifier = Modifier.height(10.dp))

            FeaturedChip(
                professor = topProfessors[0],
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .clip(shape = RoundedCornerShape(8.dp))
                    .border(
                        width = 1.dp,
                        color = golden, // golden color
                        shape = RoundedCornerShape(8.dp) // 8.dp circular edges
                    )
                    .clip(RoundedCornerShape(8.dp))
                    .clickable {
                        openProfessorDetailsDialog.value = true
                        openProfessorDetailsId.value = topProfessors[0].id
                    },
            )

            Spacer(modifier = Modifier.height(10.dp))
        }

        Spacer(modifier = Modifier.height(10.dp))

        ComponentCrousel(
            professors = topProfessors.subList(1, topProfessors.size),
            onCardClick = {
                openProfessorDetailsDialog.value = true
                openProfessorDetailsId.value = it
            }
        )

    }

}

@Composable
fun FeaturedChip(
    professor: Professor,
    modifier: Modifier = Modifier,
){
    Column(
        modifier = modifier
            .fillMaxWidth()
            .background(
                MaterialTheme.colorScheme.onSecondary,
                shape = RoundedCornerShape(8.dp)
            )
            .padding(8.dp)
        ,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(start = 10.dp)
            ) {
                CircularAvatarPlaceholder(
                    initials = "${professor.firstName[0]}",
                    size=32.dp
                )
                Text(
                    text = "${professor.lastName}, ${professor.firstName}",
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(10.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.End,
            ) {
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${professor.department}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Icon(
                        imageVector = Icons.Filled.Place,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint=golden
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${professor.overallRating}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint=golden
                    )
                }
                Row(
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${professor.numEvals}",
                        style = MaterialTheme.typography.bodySmall
                    )
                    Icon(
                        imageVector = Icons.Filled.Check,
                        contentDescription = null,
                        modifier = Modifier
                            .size(20.dp),
                        tint=golden
                    )
                }
            }
        }
    }
}


@Composable
fun CircularAvatarPlaceholder(
    initials: String,
    size: Dp,
    textColor: Color = Color.White
) {
    val backgroundColor = getBackgroundColor(initials)

    Box(
        modifier = Modifier
            .size(size)
            .border(
                BorderStroke(0.4.dp, backgroundColor),
                shape = RoundedCornerShape(6.dp)
            )
            .clip(shape = RoundedCornerShape(6.dp))
            .background(backgroundColor, shape = MaterialTheme.shapes.medium)
    ) {
        Text(
            text = initials,
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(backgroundColor, textColor),
                        startY = 0f,
                        endY = 100f
                    )
                ),
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.titleLarge,
            color = textColor
        )
    }
}

private fun getBackgroundColor(initials: String): Color {
    val colors = listOf(
        Color(0xFFE7655A),
        Color(0xFFF4B400),
        Color(0xFF23CC7A),
        Color(0xFFE76B44),
        Color(0xFF53D1CD),
        Color(0xFFE974C2)
    )

    val index = (initials.hashCode() % colors.size + colors.size) % colors.size
    return colors[index]
}