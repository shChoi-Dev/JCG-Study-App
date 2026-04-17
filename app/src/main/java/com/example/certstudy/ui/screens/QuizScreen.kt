package com.example.certstudy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.certstudy.viewmodel.QuizViewModel

@Composable
fun QuizScreen() {
    val quizViewModel: QuizViewModel = viewModel()
    val uiState by quizViewModel.uiState.collectAsStateWithLifecycle()

    if (uiState.isFinished) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "퀴즈 완료! 총 ${uiState.score}점",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Button(
                onClick = { quizViewModel.restart() },
                modifier = Modifier.padding(top = 24.dp)
            ) {
                Text("다시 풀기")
            }
        }
    } else {
        val quiz = quizViewModel.getQuiz(uiState.currentIndex)
        val isUserCorrect = uiState.selectedOptionIndex == quiz.correctIndex

        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "오늘의 퀴즈",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "${uiState.currentIndex + 1} / ${quizViewModel.quizCount}",
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.primary
            )

            Text(
                text = quiz.question,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 8.dp)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                quiz.options.forEachIndexed { index, optionText ->
                    Button(
                        onClick = { quizViewModel.onOptionSelected(index) },
                        enabled = !uiState.isEvaluated,
                        colors = quizOptionButtonColors(
                            isEvaluated = uiState.isEvaluated,
                            isCorrectOption = index == quiz.correctIndex,
                            isSelectedWrongOption = uiState.selectedOptionIndex == index &&
                                index != quiz.correctIndex
                        ),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(optionText)
                    }
                }
            }

            if (uiState.isEvaluated) {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = if (isUserCorrect) "✅ 정답입니다!" else "❌ 오답입니다!",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = if (isUserCorrect) {
                                MaterialTheme.colorScheme.tertiary
                            } else {
                                MaterialTheme.colorScheme.error
                            }
                        )
                        Text(
                            text = "해설",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text = quiz.explanation,
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                }

                Button(
                    onClick = { quizViewModel.moveToNextQuestion() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp)
                ) {
                    Text(
                        if (uiState.currentIndex == quizViewModel.quizCount - 1) {
                            "결과 보기"
                        } else {
                            "다음 문제"
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun quizOptionButtonColors(
    isEvaluated: Boolean,
    isCorrectOption: Boolean,
    isSelectedWrongOption: Boolean
): ButtonColors {
    val colorScheme = MaterialTheme.colorScheme

    return when {
        !isEvaluated -> ButtonDefaults.buttonColors()
        isCorrectOption -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.tertiaryContainer,
            contentColor = colorScheme.onTertiaryContainer,
            disabledContainerColor = colorScheme.tertiaryContainer,
            disabledContentColor = colorScheme.onTertiaryContainer
        )
        isSelectedWrongOption -> ButtonDefaults.buttonColors(
            containerColor = colorScheme.errorContainer,
            contentColor = colorScheme.onErrorContainer,
            disabledContainerColor = colorScheme.errorContainer,
            disabledContentColor = colorScheme.onErrorContainer
        )
        else -> ButtonDefaults.buttonColors(
            disabledContainerColor = colorScheme.primary.copy(alpha = 0.35f),
            disabledContentColor = colorScheme.onPrimary.copy(alpha = 0.85f)
        )
    }
}
