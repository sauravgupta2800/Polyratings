package com.example.polyratings.pages

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.polyratings.APIService
import com.example.polyratings.data.PolyratingViewModel
import com.example.polyratings.data.Professor
import com.example.polyratings.data.ReportRating
import com.example.polyratings.ui.theme.DarkGreen10
import com.example.polyratings.ui.theme.GreenGrey30
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@Composable
fun ReportReviewDialog(
    ratingId: String,
    professorId: String,
    onClose: () -> Unit,
){
    var email =  remember { mutableStateOf("") }
    var reason =  remember { mutableStateOf("") }

    //Extra
    val context = LocalContext.current
    val isLoading = remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = {
            onClose()
        },
        title = {
            Text(text = "Report Rating",
                modifier = Modifier.padding(horizontal = 5.dp),
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSecondaryContainer
            )
        },
        text = {
            Box(modifier = Modifier.fillMaxWidth()) {
                Column(
                    modifier = Modifier
                        .padding(20.dp)
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 10.dp)
                    ) {
                        CustomText(
                            value = email.value,
                            onChange = { email.value = it },
                            label = "Email (Optional)",
                            keyboardType = KeyboardType.Text
                        )
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                    ) {
                        CustomText(
                            value = reason.value,
                            onChange = {reason.value = it.trim()},
                            label = "Reason for Reporting",
                            maxLines = 5,
                            keyboardType = KeyboardType.Text
                        )
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
                    if(reason.value.isEmpty()){
                        Toast.makeText(context, "A Reason can not be empty", Toast.LENGTH_SHORT).show()
                    }else{
                        CoroutineScope(Dispatchers.IO).launch{
                            var payload = ReportRating(
                                professorId = professorId,
                                ratingId = ratingId,
                                email = email.value,
                                reason = reason.value
                            )
                            isLoading.value = true
                            println("PAYLOAD: $payload")
                            val apiService = APIService.getInstance()
                            try {
                                val response = apiService.ratingsReport(payload)
                                if (response.isSuccessful) {
                                    println("SUCCESS-FULL:")
                                    isLoading.value = false
                                    withContext(Dispatchers.Main) {
                                        Toast.makeText(context, "Thank you for the report. The team will review it soon", Toast.LENGTH_LONG).show()
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
                    }
                }
            ){
                Text(
                    text = if (isLoading.value) "We're submitting...." else "Submit",
                    modifier = Modifier.padding(4.dp),
                    fontSize = 14.sp,
                )
            }
        }

    )
}