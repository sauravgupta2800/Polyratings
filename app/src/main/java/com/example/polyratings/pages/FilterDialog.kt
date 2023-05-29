package com.example.polyratings.pages

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.SortItem
import com.example.polyratings.ui.theme.golden

@Composable
fun FilterDialog(
    viewModel: PolyratingViewModel = viewModel(),
    onClose: () -> Unit,
    selectedSortOption: String,
    selectedDeptOption: String,
    selectedOverallRating: Double,
    selectedStudentDifficulty: Double,
    selectedMaterialClearly: Double,
    selectedTotalRatings: Int,
    onSortChange: (String) -> Unit,
    onDeptChange: (String) -> Unit,
    onOverallRatingChange: (Double) -> Unit,
    onStudentdifficultyChange: (Double) -> Unit,
    onMaterialClearlyChange: (Double) -> Unit,
    onTotalRatingsChange: (Int) -> Unit
){

    val uiState by viewModel.uiState.collectAsState()

    var departmentOptions: List<String> = listOf("All") + uiState.professorList.map { it.department }.distinct()


    val sortOptions = listOf(
        SortItem("Relevant", "relevant"),
        SortItem("Alphabetical", "alphabetical"),
        SortItem("Overall Rating", "ratings"),
        SortItem("Recognizes Student Difficulty", "difficulty"),
        SortItem("Presents Material Clearly", "material"),
    )

    val isSortModalOpen = remember { mutableStateOf(false) }
    val isDeptModalOpen = remember { mutableStateOf(false) }

    println("AlertDialog")

    AlertDialog(
        onDismissRequest = {
            // Dismiss the dialog when the user clicks outside the dialog or on the back
            // button. If you want to disable that functionality, simply use an empty
            // onDismissRequest.
            onClose()
        },
        text = {

            Column(modifier = Modifier.fillMaxWidth()) {
                //SORT
                Text(
                    "Sort By:",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(18.dp))
                DropdownMenu(
                    expanded = isSortModalOpen.value,
                    onDismissRequest = { isSortModalOpen.value = false }
                ) {
                    sortOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option.name) },
                            onClick = {
                                onSortChange(option.key)
                                isSortModalOpen.value = false
                            }
                        )
                    }
                }

                Row() {
                    Text(
                        text = "${sortOptions.find { it.key== selectedSortOption}?.name}",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { isSortModalOpen.value = true }
                    )

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Arrow",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }


                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    "Filter:",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
                //DEPARTMENT:
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Department:",
                    color = Color.Black,
                )


                DropdownMenu(
                    expanded = isDeptModalOpen.value,
                    onDismissRequest = { isDeptModalOpen.value = false }
                ) {
                    departmentOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(text = option) },
                            onClick = {
                                onDeptChange(option)
                                isDeptModalOpen.value = false
                            }
                        )
                    }
                }

                Row() {
                    Text(
                        text = "${departmentOptions.find { it == selectedDeptOption}}",
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clickable { isDeptModalOpen.value = true }
                    )

                    Icon(
                        Icons.Default.KeyboardArrowDown,
                        contentDescription = "Dropdown Arrow",
                        tint = Color.Black,
                        modifier = Modifier.size(24.dp)
                    )
                }

                //Overall Ratings:
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Overall Rating:",
                    color = Color.Black,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        value = selectedOverallRating.toFloat(),
                        onValueChange = { newValue -> onOverallRatingChange(String.format("%.1f", newValue).toDouble()) },
                        valueRange = 0f..4f,
                        steps = 40,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = SliderDefaults.colors(
                            thumbColor = golden,
                            activeTrackColor = golden,
                            inactiveTrackColor = golden.copy(alpha = 0.3f)
                        ),
                    )
                    Text(
                        text = selectedOverallRating.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                //Student Difficulty:
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Recognizes Student Difficulties:",
                    color = Color.Black,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        value = selectedMaterialClearly.toFloat(),
                        onValueChange = { newValue -> onMaterialClearlyChange(String.format("%.1f", newValue).toDouble()) },
                        valueRange = 0f..4f,
                        steps = 40,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = SliderDefaults.colors(
                            thumbColor = golden,
                            activeTrackColor = golden,
                            inactiveTrackColor = golden.copy(alpha = 0.3f)
                        ),
                    )
                    Text(
                        text = selectedMaterialClearly.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                //Material Clearly:
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Presents Material Clearly:",
                    color = Color.Black,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        value = selectedStudentDifficulty.toFloat(),
                        onValueChange = { newValue -> onStudentdifficultyChange(String.format("%.1f", newValue).toDouble()) },
                        valueRange = 0f..4f,
                        steps = 40,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = SliderDefaults.colors(
                            thumbColor = golden,
                            activeTrackColor = golden,
                            inactiveTrackColor = golden.copy(alpha = 0.3f)
                        ),
                    )
                    Text(
                        text = selectedStudentDifficulty.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

                //Material Clearly:
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    "Number of Ratings:",
                    color = Color.Black,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Slider(
                        value = selectedTotalRatings.toFloat(),
                        onValueChange = { newValue -> onTotalRatingsChange(((newValue.toInt()/10)*10)) },
                        valueRange = 0f..300f,
                        steps = 30,
                        modifier = Modifier.fillMaxWidth(0.8f),
                        colors = SliderDefaults.colors(
                            thumbColor = golden,
                            activeTrackColor = golden,
                            inactiveTrackColor = golden.copy(alpha = 0.3f)
                        ),
                    )
                    Text(
                        text = selectedTotalRatings.toString(),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(start = 10.dp)
                    )
                }

            }
        },
        confirmButton = {

                Button(
                    onClick = {
                        onClose()
                    },
                    modifier = Modifier.padding(0.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = "Done",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 14.sp
                    )
                }
        },
    )
}