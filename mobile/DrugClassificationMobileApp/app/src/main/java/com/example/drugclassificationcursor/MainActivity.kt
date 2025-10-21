package com.example.drugclassificationcursor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import com.example.drugclassificationcursor.presentation.DrugScreen
import com.example.drugclassificationcursor.ui.theme.DrugClassificationCursorTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DrugClassificationCursorTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    DrugScreen()
                }
            }
        }
    }
}