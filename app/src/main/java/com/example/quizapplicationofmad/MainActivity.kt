package com.example.quizapplicationofmad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.quizapplicationofmad.ui.QuizScreen
import com.example.quizapplicationofmad.ui.theme.QuizApplicationOfMADTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            QuizApplicationOfMADTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    QuizScreen()
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun QuizScreenPreview() {
    QuizApplicationOfMADTheme {
        QuizScreen()
    }
}
