package com.example.polyratings.pages

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.Professor
import com.example.polyratings.data.SortItem
import com.example.polyratings.ui.theme.DarkGreen20
import com.example.polyratings.ui.theme.GreenGrey100
import com.example.polyratings.ui.theme.GreenGrey90
import com.example.polyratings.ui.theme.golden

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    viewModel: PolyratingViewModel = viewModel(),
){

    val uiState by viewModel.uiState.collectAsState()
    val openFilterDialog = remember { mutableStateOf(false) }
    val selectedSortOption: MutableState<String> = remember { mutableStateOf("relevant") }
    val selectedDeptOption: MutableState<String> = remember { mutableStateOf("All") }
    val selectedOverallRating: MutableState<Double> = remember { mutableStateOf(4.0) }
    val selectedStudentDifficulty: MutableState<Double> = remember { mutableStateOf(4.0) }
    val selectedMaterialClearly: MutableState<Double> = remember { mutableStateOf(4.0) }
    val selectedTotalRatings: MutableState<Int> = remember { mutableStateOf(300) }
    //Details Dialog
    val openProfessorDetailsDialog = remember { mutableStateOf(false) }
    val openProfessorDetailsId = remember { mutableStateOf("") }

    var filteredProfessors: List<Professor> = uiState.professorList.toMutableList()


    filteredProfessors = when (selectedSortOption.value) {
        "alphabetical" -> filteredProfessors.sortedBy { it.firstName }
        "ratings" -> filteredProfessors.sortedByDescending { it.overallRating }
        "difficulty" -> filteredProfessors.sortedByDescending { it.studentDifficulties }
        "material" -> filteredProfessors.sortedByDescending { it.numEvals }
        else -> filteredProfessors
    }
    filteredProfessors = when (selectedDeptOption.value) {
        "All" -> filteredProfessors
        else -> filteredProfessors.filter { it.department == selectedDeptOption.value }
    }
    filteredProfessors = filteredProfessors.filter { professor ->
        professor.overallRating <= selectedOverallRating.value &&
                professor.studentDifficulties <= selectedStudentDifficulty.value &&
                professor.materialClear <= selectedMaterialClearly.value &&
                professor.numEvals <= selectedTotalRatings.value
    }

    val query = uiState.searchKey.trim().lowercase()
    filteredProfessors = filteredProfessors.filter { professor ->
        professor.firstName.lowercase().contains(query) ||
                professor.lastName.lowercase().contains(query)
    }



    Column(modifier = Modifier
        .fillMaxSize()
        .background(GreenGrey100)) {

        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(DarkGreen20),
                contentAlignment = Alignment.Center
            ) {

                IconButton(
                    onClick = {
                        openFilterDialog.value = true
                    }
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Settings,
                        contentDescription = "Filter Icon",
                        modifier = Modifier.size(24.dp),
                        tint = Color.White,

                    )
                }
            }


            Spacer(modifier = Modifier.width(16.dp))
            TextField(
                value = uiState.searchKey,
                onValueChange = { viewModel.handleSearchKeyChange(it) },
                modifier = Modifier
                    .weight(1f)
                    .border(1.dp, DarkGreen20, shape = RoundedCornerShape(4.dp))
                ,
                placeholder = { Text("Search") },
                colors = TextFieldDefaults.textFieldColors(
                    disabledTextColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                )
            )
        }

        if(openFilterDialog.value){
            FilterDialog(
                viewModel = viewModel,
                onClose = {
                    openFilterDialog.value = false
                },
                selectedSortOption = selectedSortOption.value,
                selectedDeptOption = selectedDeptOption.value,
                selectedOverallRating = selectedOverallRating.value,
                selectedStudentDifficulty = selectedStudentDifficulty.value,
                selectedMaterialClearly = selectedMaterialClearly.value,
                selectedTotalRatings = selectedTotalRatings.value,
                onSortChange = { selectedSortOption.value = it},
                onDeptChange = { selectedDeptOption.value = it },
                onOverallRatingChange = { selectedOverallRating.value = it },
                onStudentdifficultyChange = {selectedStudentDifficulty.value = it},
                onMaterialClearlyChange = { selectedMaterialClearly.value = it },
                onTotalRatingsChange = { selectedTotalRatings.value = it}
            )
        }

        if(openProfessorDetailsDialog.value){
            ProfessorDetailsDialog(
                id = openProfessorDetailsId.value,
                onClose = {
                    openProfessorDetailsId.value = ""
                    openProfessorDetailsDialog.value = false
                },
            )
        }

        LazyColumn(
            modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {
            items(filteredProfessors) { item ->
                FeaturedChip(
                    professor = item,
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth(1f)
                        .clip(shape = RoundedCornerShape(8.dp))
                        .border(
                            width = 1.dp,
                            color = golden, // golden color
                            shape = RoundedCornerShape(8.dp) // 8.dp circular edges
                        )
                        .clip(RoundedCornerShape(8.dp))
                        .clickable {
                            openProfessorDetailsDialog.value = true
                            openProfessorDetailsId.value = item.id
                        }
                )
            }
        }
    }
}