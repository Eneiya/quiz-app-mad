package com.example.quizapplicationofmad.ui

import com.example.quizapplicationofmad.model.Question

data class QuizUiState(
    val questions: List<Question> = emptyList(),
    val currentQuestionIndex: Int = 0,
    val selectedOptionIndex: Int? = null,
    val userAnswers: Map<Int, Int> = emptyMap(),
    val score: Int = 0,
    val isQuizCompleted: Boolean = false
) {
    val currentQuestion: Question?
        get() = questions.getOrNull(currentQuestionIndex)

    val totalQuestions: Int
        get() = questions.size

    val progress: Float
        get() = if (totalQuestions > 0) (currentQuestionIndex + 1).toFloat() / totalQuestions else 0f
}
