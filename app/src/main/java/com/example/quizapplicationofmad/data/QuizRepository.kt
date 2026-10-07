package com.example.quizapplicationofmad.data

import com.example.quizapplicationofmad.model.Question

object QuizRepository {
    fun getQuestions(): List<Question> {
        return listOf(
            Question(
                id = 1,
                text = "What is the official programming language recommended by Google for Android development?",
                options = listOf("Java", "Kotlin", "Swift", "C#"),
                correctAnswerIndex = 1
            ),
            Question(
                id = 2,
                text = "Which Jetpack Compose layout component places its children in a vertical sequence?",
                options = listOf("Row", "Box", "Column", "LazyRow"),
                correctAnswerIndex = 2
            ),
            Question(
                id = 3,
                text = "In Android, which file is used to declare application components, permissions, and hardware features?",
                options = listOf("build.gradle.kts", "MainActivity.kt", "strings.xml", "AndroidManifest.xml"),
                correctAnswerIndex = 3
            ),
            Question(
                id = 4,
                text = "What is the primary purpose of a ViewModel in Android Architecture Components?",
                options = listOf(
                    "Managing database queries directly",
                    "Holding and managing UI-related data in a lifecycle-conscious way",
                    "Designing the user interface layout",
                    "Handling raw network socket connections"
                ),
                correctAnswerIndex = 1
            ),
            Question(
                id = 5,
                text = "Which state holder in Jetpack Compose preserves state across configuration changes like screen rotation?",
                options = listOf("remember { }", "rememberSaveable { }", "mutableStateOf { }", "derivedStateOf { }"),
                correctAnswerIndex = 1
            ),
            Question(
                id = 6,
                text = "Which Android component is responsible for performing background tasks without a direct user interface?",
                options = listOf("Activity", "Service", "BroadcastReceiver", "ContentProvider"),
                correctAnswerIndex = 1
            )
        )
    }
}
