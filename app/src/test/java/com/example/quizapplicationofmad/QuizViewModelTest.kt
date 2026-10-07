package com.example.quizapplicationofmad

import com.example.quizapplicationofmad.ui.QuizViewModel
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class QuizViewModelTest {

    private lateinit var viewModel: QuizViewModel

    @Before
    fun setUp() {
        viewModel = QuizViewModel()
    }

    @Test
    fun initialQuizState_hasQuestionsAndZeroScore() {
        val state = viewModel.uiState.value
        assertTrue(state.questions.size >= 5)
        assertEquals(0, state.currentQuestionIndex)
        assertEquals(0, state.score)
        assertNull(state.selectedOptionIndex)
        assertFalse(state.isQuizCompleted)
    }

    @Test
    fun selectOption_updatesSelectedOptionIndex() {
        viewModel.selectOption(2)
        assertEquals(2, viewModel.uiState.value.selectedOptionIndex)
    }

    @Test
    fun submitCorrectAnswer_incrementsScoreAndAdvancesQuestion() {
        val firstQuestion = viewModel.uiState.value.currentQuestion!!
        val correctOption = firstQuestion.correctAnswerIndex

        viewModel.selectOption(correctOption)
        viewModel.submitAnswerAndNext()

        val newState = viewModel.uiState.value
        assertEquals(1, newState.currentQuestionIndex)
        assertEquals(1, newState.score)
        assertNull(newState.selectedOptionIndex)
    }

    @Test
    fun submitIncorrectAnswer_doesNotIncrementScore() {
        val firstQuestion = viewModel.uiState.value.currentQuestion!!
        val incorrectOption = (firstQuestion.correctAnswerIndex + 1) % firstQuestion.options.size

        viewModel.selectOption(incorrectOption)
        viewModel.submitAnswerAndNext()

        val newState = viewModel.uiState.value
        assertEquals(1, newState.currentQuestionIndex)
        assertEquals(0, newState.score)
        assertNull(newState.selectedOptionIndex)
    }

    @Test
    fun completingQuiz_setsIsQuizCompletedToTrue() {
        val totalQuestions = viewModel.uiState.value.totalQuestions

        for (i in 0 until totalQuestions) {
            val currentQuestion = viewModel.uiState.value.currentQuestion!!
            viewModel.selectOption(currentQuestion.correctAnswerIndex)
            viewModel.submitAnswerAndNext()
        }

        val finalState = viewModel.uiState.value
        assertTrue(finalState.isQuizCompleted)
        assertEquals(totalQuestions, finalState.score)
    }

    @Test
    fun restartQuiz_resetsAllState() {
        val currentQuestion = viewModel.uiState.value.currentQuestion!!
        viewModel.selectOption(currentQuestion.correctAnswerIndex)
        viewModel.submitAnswerAndNext()

        viewModel.restartQuiz()

        val resetState = viewModel.uiState.value
        assertEquals(0, resetState.currentQuestionIndex)
        assertEquals(0, resetState.score)
        assertNull(resetState.selectedOptionIndex)
        assertFalse(resetState.isQuizCompleted)
    }
}
