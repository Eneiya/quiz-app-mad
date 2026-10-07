package com.example.quizapplicationofmad.ui

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.quizapplicationofmad.ui.components.OptionCard
import com.example.quizapplicationofmad.ui.components.ResultContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuizScreen(
    modifier: Modifier = Modifier,
    viewModel: QuizViewModel = remember { QuizViewModel() }
) {
    val uiState by viewModel.uiState.collectAsState()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Multiple-Choice Quiz",
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        AnimatedContent(
            targetState = uiState.isQuizCompleted,
            label = "QuizStateTransition",
            modifier = Modifier.padding(innerPadding)
        ) { isCompleted ->
            if (isCompleted) {
                ResultContent(
                    score = uiState.score,
                    totalQuestions = uiState.totalQuestions,
                    onRestartQuiz = { viewModel.restartQuiz() }
                )
            } else {
                QuestionContent(
                    uiState = uiState,
                    onOptionSelected = { viewModel.selectOption(it) },
                    onSubmitNext = { viewModel.submitAnswerAndNext() }
                )
            }
        }
    }
}

@Composable
fun QuestionContent(
    uiState: QuizUiState,
    onOptionSelected: (Int) -> Unit,
    onSubmitNext: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentQuestion = uiState.currentQuestion ?: return
    val optionLetters = listOf("A", "B", "C", "D")

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Question ${uiState.currentQuestionIndex + 1} of ${uiState.totalQuestions}",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            LinearProgressIndicator(
                progress = { uiState.progress },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
                    .clip(RoundedCornerShape(4.dp)),
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                ),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
            ) {
                Text(
                    text = currentQuestion.text,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(20.dp),
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            currentQuestion.options.forEachIndexed { index, optionText ->
                OptionCard(
                    optionLetter = optionLetters.getOrElse(index) { "${index + 1}" },
                    optionText = optionText,
                    isSelected = uiState.selectedOptionIndex == index,
                    onSelect = { onOptionSelected(index) },
                    modifier = Modifier.padding(vertical = 6.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onSubmitNext,
            enabled = uiState.selectedOptionIndex != null,
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp),
            shape = RoundedCornerShape(16.dp)
        ) {
            val buttonText = if (uiState.currentQuestionIndex == uiState.totalQuestions - 1) {
                "Submit Quiz"
            } else {
                "Next Question"
            }
            Text(
                text = buttonText,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
