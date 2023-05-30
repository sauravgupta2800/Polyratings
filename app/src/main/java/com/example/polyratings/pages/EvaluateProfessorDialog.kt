package com.example.polyratings.pages

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.polyratings.APIService
import com.example.polyratings.ApiResponseDetails
import com.example.polyratings.data.Constants.COURSE_TYPES
import com.example.polyratings.data.Constants.DEPARTMENT_LIST
import com.example.polyratings.data.Constants.GRADES
import com.example.polyratings.data.Constants.GRADE_LEVELS
import com.example.polyratings.data.EvaluateProfessorFormState
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.Professor
import com.example.polyratings.ui.theme.GreenGrey30
import com.example.polyratings.ui.theme.GreenGrey50
import com.example.polyratings.ui.theme.GreenGrey60
import com.example.polyratings.ui.theme.Grey100
import com.example.polyratings.ui.theme.Grey90
import com.example.polyratings.ui.theme.Grey95
import com.example.polyratings.ui.theme.Grey99
import com.example.polyratings.ui.theme.golden
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response
import javax.security.auth.callback.Callback
import androidx.lifecycle.viewModelScope
import com.example.polyratings.ui.theme.DarkGreen10
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EvaluateProfessorDialog(
    professor: Professor,
    onClose: () -> Unit,
    viewModel: PolyratingViewModel = viewModel(),
){

    var selectedCourse = remember { mutableStateOf(professor.courses[0]) }
    var department = remember { mutableStateOf(professor.courses[0].split(" ")[0]) }
    var courseNum = remember { mutableStateOf(professor.courses[0].split(" ")[1].toInt()) }
    //straight Dropdown
    var gradeLevel = remember {mutableStateOf(GRADE_LEVELS[0])}
    var grade = remember {mutableStateOf(GRADES[0])}
    var courseType = remember {mutableStateOf(COURSE_TYPES[0])}
    //Rating Range [0-4]
    var overallRating = remember {mutableStateOf(4)}
    var recognizesStudentDifficulties = remember {mutableStateOf(4)}
    var presentsMaterialClearly = remember {mutableStateOf(4)}
    // Actual Rating
    var rating =  remember {mutableStateOf("")}

    //Extra
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }



    AlertDialog(
        onDismissRequest = {
//            onClose()
        },
        title = {
            Row() {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(5.dp),
                    tint=golden
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(15.dp),
                    tint=golden
                )
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(30.dp),
                    tint=golden
                )
                Text(text = "Evaluate Professor",
                    modifier = Modifier.padding(horizontal = 5.dp),
                    fontWeight = FontWeight.ExtraBold,
                    color = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }

        },
        text = {
            Box(modifier = Modifier.fillMaxSize()) {
                LazyColumn(modifier = Modifier.fillMaxSize()){
                    items(1) { index->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            DropdownComponent(
                                list = professor.courses + "Other",
                                value = selectedCourse.value,
                                onSelect = {
                                    selectedCourse.value = it
                                    val courseCode = it.split(" ")
                                    if (courseCode.size == 2) {
                                        department.value = courseCode[0]
                                        courseNum.value = courseCode[1].toInt()
                                    } else {
                                        department.value = ""
                                        courseNum.value = 0
                                    }
                                },
                                label = "Course",
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            DropdownComponent(
                                disabled = selectedCourse.value != "Other",
                                list = DEPARTMENT_LIST,
                                value = department.value,
                                onSelect = { department.value = it },
                                label = "Department",
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomText(
                                disabled = selectedCourse.value != "Other",
                                value = (if (courseNum.value == 0) "" else courseNum.value).toString(),
                                onChange = {
                                    val numericValue = it.filter { it.isDigit() }
                                    courseNum.value = if (numericValue.isNotEmpty()) numericValue.toInt() else 0 },
                                label = "Course Num# ",
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            DropdownComponent(
                                list = GRADE_LEVELS,
                                value = gradeLevel.value,
                                onSelect = { gradeLevel.value = it },
                                label = "Year",
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            DropdownComponent(
                                list = GRADES,
                                value = grade.value,
                                onSelect = { grade.value = it },
                                label = "Grade Achieved",
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            DropdownComponent(
                                list = COURSE_TYPES,
                                value = courseType.value,
                                onSelect = { courseType.value = it },
                                label = "Reason for Taking",
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomSlider(
                                value = overallRating.value,
                                onChange = { overallRating.value = it },
                                label = "Overall Rating",
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomSlider(
                                value = recognizesStudentDifficulties.value,
                                onChange = { recognizesStudentDifficulties.value = it },
                                label = "Recognizes Difficulties",
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomSlider(
                                value = presentsMaterialClearly.value,
                                onChange = { presentsMaterialClearly.value = it },
                                label = "Presents Clearly",
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomText(
                                value = if(rating.value .isEmpty()) "\n\n\n\n" else rating.value,
                                onChange = {rating.value = it},
                                label = "Rating",
                                maxLines = 5,
                                keyboardType = KeyboardType.Text
                            )
                        }

                    }
                }
            }

        },
        dismissButton = {
            Text(
                text = "Cancel",
                color = DarkGreen10,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 10.dp).padding(top = 15.dp).clickable(onClick = onClose),
            )
        },
        confirmButton = {
                Button(
                    enabled = !isLoading.value,
                    onClick = {

                        val (isValid, errorMessage) = validateFields(
                            department.value,
                            courseNum.value,
                            rating.value
                        )
                        if (isValid) {

                            CoroutineScope(Dispatchers.IO).launch {
                                var payload = EvaluateProfessorFormState(
                                    professor = professor.id,
                                    department = department.value,
                                    courseNum = courseNum.value,
                                    overallRating = overallRating.value,
                                    presentsMaterialClearly = presentsMaterialClearly.value,
                                    recognizesStudentDifficulties = recognizesStudentDifficulties.value,
                                    grade = grade.value,
                                    courseType = courseType.value,
                                    rating = rating.value.trim()
                                )
                                isLoading.value = true
                                println("PAYLOAD: $payload")
                                val apiService = APIService.getInstance()
                                try {
                                    val response = apiService.addRating(payload)
                                    if (response.isSuccessful) {
                                        val apiResponse: ApiResponseDetails? = response.body()
                                        val professor: Professor =
                                            (apiResponse?.result?.data ?: null) as Professor;

                                        println("AFTER POST DATA:")
                                        println(professor)
                                        viewModel.updateCurrentProfessor(professor)
                                        isLoading.value = false
                                        withContext(Dispatchers.Main) {
                                           Toast.makeText(context, "Thank you for your rating", Toast.LENGTH_LONG).show()
                                            onClose()
                                        }
                                    } else {
                                        println(response)
                                    }


                                } catch (e: Exception) {
                                    isLoading.value = false
                                    println("ERROR IN POSTING!!")
                                    println(e.message.toString())
                                }
                            }
                        } else {
                            Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                        }
                    },
                    modifier = Modifier.padding(0.dp),
                    elevation = ButtonDefaults.buttonElevation(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                        contentColor = MaterialTheme.colorScheme.onSecondary,
                        disabledContentColor = Color.White,
                        disabledContainerColor = GreenGrey30
                    ),
                    shape = RoundedCornerShape(4.dp)
                ) {
                    Text(
                        text = if (isLoading.value) "All Good! We're Submitting..." else "Submit",
                        modifier = Modifier.padding(4.dp),
                        fontSize = 14.sp,
                    )
                }
        }
    )
}

fun validateFields(
    department: String,
    courseNum: Int,
    rating: String
): Pair<Boolean, String> {
    // Perform your field validation logic here
    val isCourseDepartmentValid = DEPARTMENT_LIST.contains(department)
    val isCourseNumberValid = courseNum in 100..599
    val isRatingTextValid = rating.length >= 20

    if(!isCourseDepartmentValid){
        return Pair(false, "Choose department")
    }
    if (!isCourseNumberValid) {
        return Pair(false, "Course Number should be between 100 and 599")
    }
    if (!isRatingTextValid) {
        return Pair(false, "Rating text must be at least 20 characters long")
    }
    return Pair(true, "")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DropdownComponent(
    list: List<String> = listOf(),
    value: String = "",
    onSelect: (String)->Unit,
    label: String = "",
    modifier: Modifier = Modifier,
    disabled: Boolean = false
) {
    var expanded = remember { mutableStateOf(false) }

    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        ExposedDropdownMenuBox(
            expanded = expanded.value,
            onExpandedChange = {
                expanded.value = !expanded.value
            }
        ) {

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(bottom = 5.dp)
                )
                if(disabled)
                    TextField(
                        value = value,
                        onValueChange = {},
                        readOnly = true,
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Grey90,
                            textColor = Color.Gray
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                else
                    TextField(
                        value = value,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
                        modifier = Modifier.menuAnchor().fillMaxWidth(),
                        colors = TextFieldDefaults.textFieldColors(
                            containerColor = Grey99,
                            textColor = Color.Black
                        )
                    )
            }

            ExposedDropdownMenu(
                expanded = expanded.value,
                onDismissRequest = { expanded.value = false }
            ) {
                list.forEach { item ->
                    DropdownMenuItem(
                        text = { Text(text = item, fontWeight = if(value==item) FontWeight.Bold else FontWeight.Medium) },
                        onClick = {
                            onSelect(item)
                            expanded.value = false
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun CustomSlider(
    value: Int,
    onChange: (Int)->Unit,
    label: String = "",
    modifier: Modifier = Modifier,
){
    Box(
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            Row(
                modifier = Modifier.fillMaxWidth(1f),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Slider(
                    value = value.toFloat(),
                    onValueChange = { newValue ->
                        onChange(
                            newValue.toInt()
                        )
                    },
                    valueRange = 0f..4f,
                    steps = 4,
                    colors = SliderDefaults.colors(
                        thumbColor = golden,
                        activeTrackColor = golden,
                        inactiveTrackColor = golden.copy(alpha = 0.3f)
                    ),
                    modifier = Modifier.fillMaxWidth(0.8f)
                )
                Text(
                    text = value.toString(),
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomText(
    value: String = "",
    onChange: (String)->Unit,
    label: String = "",
    modifier: Modifier = Modifier,
    disabled: Boolean = false,
    maxLines: Int = 1,
    keyboardType: KeyboardType = KeyboardType.Number
){
    Box(
        modifier = modifier.fillMaxWidth()
    ){
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = label,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 5.dp)
            )
            if(disabled)
                TextField(
                    value = value,
                    readOnly = true,
                    onValueChange = {},
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Grey90,
                        textColor = Color.Gray
                    )
                )
            else TextField(
                    value = value,
                    onValueChange = { onChange(it)},
                    readOnly = false,
                    colors = TextFieldDefaults.textFieldColors(
                        containerColor = Grey99,
                        textColor = Color.Black
                    ),
                modifier = Modifier.fillMaxWidth(),
                maxLines = maxLines,
                keyboardOptions = KeyboardOptions(keyboardType=keyboardType)

            )
        }

    }
}