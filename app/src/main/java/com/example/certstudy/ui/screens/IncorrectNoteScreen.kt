package com.example.certstudy.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.certstudy.data.IncorrectQuiz
import com.example.certstudy.viewmodel.AppViewModelProvider
import com.example.certstudy.viewmodel.IncorrectNoteViewModel

@Composable
fun IncorrectNoteScreen() {
    val viewModel: IncorrectNoteViewModel = viewModel(factory = AppViewModelProvider.Factory)
    val incorrectQuizzes by viewModel.incorrectQuizzes.collectAsStateWithLifecycle(initialValue = emptyList())

    if (incorrectQuizzes.isEmpty()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "아직 오답 노트가 없습니다.",
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = "퀴즈에서 틀린 문제는 자동으로 여기에 저장됩니다.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    } else {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = incorrectQuizzes,
                key = { it.id }
            ) { quiz ->
                IncorrectQuizCard(
                    quiz = quiz,
                    onDelete = { viewModel.delete(quiz) }
                )
            }
        }
    }
}

@Composable
private fun IncorrectQuizCard(
    quiz: IncorrectQuiz,
    onDelete: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { expanded = !expanded },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = quiz.question,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "정답 보기 / 해설 보기",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
            if (expanded) {
                val options = listOf(quiz.option1, quiz.option2, quiz.option3, quiz.option4)
                val correctAnswer = options[quiz.correctIndex]
                val selectedAnswer = options.getOrNull(quiz.selectedOptionIndex) ?: "선택 정보 없음"
                Text(
                    text = "✅ 정답: ${quiz.correctIndex + 1}. $correctAnswer",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Text(
                    text = "❌ 내가 고른 답: ${quiz.selectedOptionIndex + 1}. $selectedAnswer",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.error
                )
                Text(
                    text = "해설: ${quiz.explanation}",
                    style = MaterialTheme.typography.bodyMedium
                )
                IconButton(
                    onClick = onDelete,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "오답 삭제"
                    )
                }
            }
        }
    }
}
