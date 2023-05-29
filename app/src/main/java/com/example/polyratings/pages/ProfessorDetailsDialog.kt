package com.example.polyratings.pages

import android.annotation.SuppressLint
import android.widget.RatingBar
import androidx.compose.runtime.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.Professor
import com.example.polyratings.data.Review
import com.example.polyratings.data.UiState
import com.example.polyratings.ui.theme.DarkGreen10
import com.example.polyratings.ui.theme.DarkGreen20
import com.example.polyratings.ui.theme.Green10
import com.example.polyratings.ui.theme.GreenGrey100
import com.example.polyratings.ui.theme.Grey10
import com.example.polyratings.ui.theme.Grey20
import com.example.polyratings.ui.theme.Grey30
import com.example.polyratings.ui.theme.Grey95
import com.example.polyratings.ui.theme.golden
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ProfessorDetailsDialog(
    id:String,
    onClose: () -> Unit,
    viewModel: PolyratingViewModel = viewModel(),
){

    val uiState by viewModel.uiState.collectAsState()
    val professor: Professor? = uiState.currentProfessor;
    val fetching: Boolean = uiState.fetchingCurrentProfessor;
    val openEvaluateDialog = remember { mutableStateOf(false) }

    LaunchedEffect(Unit, block = {
        if(id.isNotEmpty())
            viewModel.getProfessorDetails(id)
        print("Calling LaunchedEffect of ProfessorDetailsDialog !!!!")
    })

    LaunchedEffect(Unit) {
        viewModel.getProfessorDetails(id)
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onClose() }) {

        Scaffold(
            topBar = { DialogHeader(
                title = "Professor Detail",
                onBackClick = { onClose() }
            )}) {

            if(fetching)
                MainLoader(customGreeting ="Hold on!")
            else
                Box(modifier = Modifier
                    .padding(it)
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .background(GreenGrey100)) {

                    Column() {
                        
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()){
                            if (professor != null) {
                                ProfHeadDetails(professor)
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 15.dp)
                                .padding(bottom = 70.dp)
                                .padding(top = 0.dp)
                        ) {
                            DisplayReviews(professor?.reviews?: mutableMapOf())
                        }
                    }


                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(DarkGreen10)
                            .padding(vertical = 12.dp)
                            .align(Alignment.BottomCenter),
                        horizontalArrangement = Arrangement.Center,
                    ) {

                        BottomCourses(professor?.courses ?: listOf())
                    }

                    EvaluateButton(onEvaluate = {openEvaluateDialog.value = true})
                    if(openEvaluateDialog.value)
                        professor?.let { it1 -> EvaluateProfessorDialog(professor = it1, onClose={openEvaluateDialog.value = false}) }

                }
        }
    }
}

@Composable
fun EvaluateButton(
    onEvaluate: ()-> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
        contentAlignment = Alignment.BottomEnd
    ) {
        // Content of the page

        FloatingActionButton(
            onClick = { onEvaluate() },
            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
            contentColor = Color.White,
            elevation = FloatingActionButtonDefaults.elevation(5.dp),
            modifier = Modifier.padding(end = 0.dp, bottom = 65.dp)
        ) {
            Icon(Icons.Filled.Add, contentDescription = "Add")
        }
    }
}

@Composable
fun DisplayReviews(reviewsMap: MutableMap<String, List<Review>>) {


    LazyColumn {
        itemsIndexed(reviewsMap.entries.toList()){ index, entry->
            val (key, reviews) = entry



            reviews.forEach { review ->
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp, start = 5.dp)
//                        .padding(top = if(index!=0) 20.dp else 5.dp)
                        .background(golden, shape = RoundedCornerShape(0.dp)) // Golden color
                ) {
                    Text(
                        text = key,
                        color = Color.White,
                        style = TextStyle(
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        modifier = Modifier
                            .padding(horizontal = 10.dp, vertical = 5.dp)
                            .align(Alignment.Center)
                    )
                }
                Row(
                    modifier = Modifier
                        .padding()
                        .clip(shape = RoundedCornerShape(8.dp))
                ) {
                    SingleReviewCard(review,onFlag={})
                }
            }

        }
    }

}

@Composable
fun BottomCourses(
    courses: List<String> = listOf("MTD 401","Univ 400","CSC 213", "CSC 514", "CSC 223", "CSC 414")
){
    LazyRow(
        modifier = Modifier
            .padding(bottom = 8.dp)
    ) {
        itemsIndexed(courses) { index, chipText ->
            Box(
                modifier = Modifier
                    .padding(start = if (index == 0) 0.dp else 8.dp)
                    .background(golden, shape = RoundedCornerShape(5.dp)) // Golden color
            ) {
                Text(
                    text = chipText,
                    color = Color.White,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    modifier = Modifier
                        .padding(horizontal = 10.dp, vertical = 5.dp)
                        .align(Alignment.Center)
                )
            }
        }
    }
}
@Composable
fun ProfHeadDetails(professor: Professor){
//    val gradientBrush = Brush.horizontalGradient(
//        colors = listOf(DarkGreen20, golden)
//    )
//    modifier = Modifier
//        .background(brush = gradientBrush)
//        .padding(16.dp)

    var rating by remember { mutableStateOf(3.7f) }


    Column(modifier = Modifier
        .fillMaxWidth()
        .padding(10.dp)) {

            Text(
                text = "${professor.department} Prof",
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.SemiBold
                ),
                color = Grey30
            )
            Text(
                text = "${professor.lastName}, ${professor.firstName}",
                style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Grey10
            )
        Spacer(modifier = Modifier.height(10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .shadow(10.dp, spotColor = Color.Gray, clip = false)
                .background(Color.White)
                .clip(shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .padding(10.dp)
            ) {
                //Overall Ratings
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "${professor.overallRating}",
                        style = TextStyle(
                            fontSize = 35.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Green10
                    )
                    Column(
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(
                            text = "${professor.numEvals} ${if(professor.numEvals>1) "Evaluations" else "Evaluation"}",
                            style = TextStyle(
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold
                            ),
                            color = Grey30
                        )
                        RatingBar(rating = professor.overallRating, iconModified = Modifier.size(30.dp))
                    }
                }

                //Recognizes Difficulties
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Grey95)
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Recognizes Difficulties",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = Grey30
                    )

                    RatingBar(rating = professor.studentDifficulties, iconModified = Modifier.size(20.dp))

                }
                // Presents Clearly
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Grey95)
                        .padding(vertical = 5.dp, horizontal = 10.dp)
                        .clip(shape = RoundedCornerShape(10.dp)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "Presents Clearly",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Medium
                        ),
                        color = Grey30
                    )

                    RatingBar(rating = professor.materialClear, iconModified = Modifier.size(20.dp))

                }

            }
        }


    }
}
