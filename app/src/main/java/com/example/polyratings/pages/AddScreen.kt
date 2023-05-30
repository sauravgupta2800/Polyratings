package com.example.polyratings.pages

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.polyratings.APIService
import com.example.polyratings.ApiResponseDetails
import com.example.polyratings.data.AddProfessorFormState
import com.example.polyratings.data.Constants
import com.example.polyratings.data.Constants.DEPARTMENT_LIST
import com.example.polyratings.data.EvaluateProfessorFormState
import com.example.polyratings.data.Professor
import com.example.polyratings.ui.theme.GreenGrey30
import com.example.polyratings.ui.theme.Grey95
import com.example.polyratings.ui.theme.golden
import com.example.polyratings.ui.theme.goldenlight
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun AddScreen(
    modifier: Modifier = Modifier,
    onSuccess: ()->Unit
){
    // Professor
    var firstName =  remember { mutableStateOf("") }
    var lastName =  remember { mutableStateOf("") }
    var departmentProf = remember { mutableStateOf(DEPARTMENT_LIST[0]) }
    // Rating
    var department = remember { mutableStateOf(DEPARTMENT_LIST[0]) }
    var courseNum = remember { mutableStateOf(0) }

    var gradeLevel = remember {mutableStateOf(Constants.GRADE_LEVELS[0])}
    var grade = remember {mutableStateOf(Constants.GRADES[0])}
    var courseType = remember {mutableStateOf(Constants.COURSE_TYPES[0])}

    var overallRating = remember {mutableStateOf(4)}
    var recognizesStudentDifficulties = remember {mutableStateOf(4)}
    var presentsMaterialClearly = remember {mutableStateOf(4)}

    var rating =  remember {mutableStateOf("")}

    //Extra
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }


    Box(modifier = Modifier
        .fillMaxSize()
        .background(Grey95)) {
        LazyColumn(modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp)){
            items(1) { index ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp)
                            .background(golden, shape = RoundedCornerShape(0.dp)) // Golden color
                    ) {
                        Text(
                            text = "PROFESSOR",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(2.dp, golden),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(goldenlight)

                ){
                    Column(modifier = Modifier
                        .padding(20.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomText(
                                value = firstName.value,
                                onChange = { firstName.value = it },
                                label = "First Name",
                                keyboardType = KeyboardType.Text
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            CustomText(
                                value = lastName.value,
                                onChange = { lastName.value = it },
                                label = "Last Name",
                                keyboardType = KeyboardType.Text
                            )
                        }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                        ) {
                            DropdownComponent(
                                list = DEPARTMENT_LIST,
                                value = departmentProf.value,
                                onSelect = {
                                    departmentProf.value = it
                                    department.value = it },
                                label = "Department",
                            )
                        }
                    }
                }


                // Ratings
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .padding(top = 10.dp, start = 10.dp)
                            .background(golden, shape = RoundedCornerShape(0.dp)) // Golden color
                    ) {
                        Text(
                            text = "RATING",
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

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            BorderStroke(2.dp, golden),
                            shape = RoundedCornerShape(5.dp)
                        )
                        .background(goldenlight)

                ){
                    Column(modifier = Modifier
                        .padding(20.dp)
                    ) {

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = 10.dp)
                        ) {
                            DropdownComponent(
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
                                list = Constants.GRADE_LEVELS,
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
                                list = Constants.GRADES,
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
                                list = Constants.COURSE_TYPES,
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

                Row(
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth().padding(vertical = 10.dp)
                    ) {
                    Button(
                        enabled = !isLoading.value,
                        modifier = Modifier.padding(0.dp),
                        elevation = ButtonDefaults.buttonElevation(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.onSecondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            disabledContentColor = Color.White,
                            disabledContainerColor = GreenGrey30
                        ),
                        shape = RoundedCornerShape(4.dp),
                        onClick = {
                            val (isValid, errorMessage) = addScreenValidateFields(
                                firstName.value,
                                lastName.value,
                                courseNum.value,
                                rating.value
                            )
                            if(!isValid){
                                Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show()
                            }else{
                                CoroutineScope(Dispatchers.IO).launch{
                                    var payload = AddProfessorFormState(
                                        firstName = firstName.value,
                                        lastName = lastName.value,
                                        department = departmentProf.value,
                                        rating = EvaluateProfessorFormState(
                                            department = department.value,
                                            courseNum = courseNum.value,
                                            overallRating = overallRating.value,
                                            presentsMaterialClearly = presentsMaterialClearly.value,
                                            recognizesStudentDifficulties = recognizesStudentDifficulties.value,
                                            grade = grade.value,
                                            courseType = courseType.value,
                                            rating = rating.value.trim()
                                        )
                                    )

                                    isLoading.value = true
                                    println("PAYLOAD: $payload")
                                    val apiService = APIService.getInstance()
                                    try {
                                        val response = apiService.addProfessor(payload)
                                        if (response.isSuccessful) {
                                            println("SUCCESS-FULL:")
                                            isLoading.value = false
                                            withContext(Dispatchers.Main) {
                                                onSuccess()
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
                            }
                        },
                    ){
                        Text(
                            text = if (isLoading.value) "All Good! We're Adding the Prof..." else "Add Professor",
                            modifier = Modifier.padding(4.dp),
                            fontSize = 14.sp,
                        )
                    }
                }
            }
        }
    }
}



fun addScreenValidateFields(
    firstName: String,
    lastName:String,
    courseNum: Int,
    rating: String
): Pair<Boolean, String> {
    // Perform your field validation logic here
    val isCourseNumberValid = courseNum in 100..599
    val isRatingTextValid = rating.length >= 20
    if(firstName.trim().isEmpty()){
        return Pair(false, "Prof. First Name cannot be empty.")
    }
    if(lastName.trim().isEmpty()){
        return Pair(false, "Prof. Last Name cannot be empty.")
    }
    if (!isCourseNumberValid) {
        return Pair(false, "Course Number should be between 100 and 599")
    }
    if (!isRatingTextValid) {
        return Pair(false, "Rating text must be at least 20 characters long")
    }
    return Pair(true, "")
}