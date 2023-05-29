package com.example.polyratings

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.polyratings.pages.MainLoader
//import com.example.polyratings.pages.NavigationPage
import com.example.polyratings.ui.theme.PolyratingsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolyratingsTheme {
                Surface (
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ){
                    PolyratingsApp()
                }
            }
        }
    }
}



//onSelectionChanged: (String) -> Unit = {},
//onCancelButtonClicked: () -> Unit = {},
//onNextButtonClicked: () -> Unit = {},
//
//
//
//onClick = {
//    selectedValue = item
//    onSelectionChanged(item)
//}