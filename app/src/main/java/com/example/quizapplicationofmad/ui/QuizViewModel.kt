package com.example.quizapplicationofmad.ui

import androidx.lifecycle.ViewModel
import com.example.quizapplicationofmad.data.QuizRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class QuizViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        loadQuestions()
    }

    private fun loadQuestions() {
        val questions = QuizRepository.getQuestions()
        _uiState.value = QuizUiState(
            questions = questions,
            currentQuestionIndex = 0,
            selectedOptionIndex = null,
            userAnswers = emptyMap(),
            score = 0,
            isQuizCompleted = false
        )
    }

    fun selectOption(optionIndex: Int) {
        _uiState.update { currentState ->
            currentState.copy(selectedOptionIndex = optionIndex)
        }
    }

    fun submitAnswerAndNext() {
        val currentState = _uiState.value
        val currentQuestion = currentState.currentQuestion ?: return
        val selectedOption = currentState.selectedOptionIndex ?: return

        val isCorrect = selectedOption == currentQuestion.correctAnswerIndex
        val newScore = if (isCorrect) currentState.score + 1 else currentState.score
        val newAnswers = currentState.userAnswers + (currentState.currentQuestionIndex to selectedOption)

        val isLastQuestion = currentState.currentQuestionIndex >= currentState.totalQuestions - 1

        if (isLastQuestion) {
            _uiState.update {
                it.copy(
                    userAnswers = newAnswers,
                    score = newScore,
                    isQuizCompleted = true
                )
            }
        } else {
            _uiState.update {
                it.copy(
                    userAnswers = newAnswers,
                    score = newScore,
                    currentQuestionIndex = it.currentQuestionIndex + 1,
                    selectedOptionIndex = null
                )
            }
        }
    }

    fun restartQuiz() {
        loadQuestions()
    }
}
