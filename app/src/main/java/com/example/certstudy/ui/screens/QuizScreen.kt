package com.example.certstudy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.certstudy.model.QuizType
import com.example.certstudy.viewmodel.StudyViewModel

@Composable
fun QuizScreen(studyViewModel: StudyViewModel) {
    val quizItems = studyViewModel.quizzes
    var currentIndex by rememberSaveable { mutableStateOf(0) }
    var userAnswer by rememberSaveable { mutableStateOf("") }
    var resultText by remember { mutableStateOf<String?>(null) }

    val currentQuiz = quizItems[currentIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "퀴즈 ${currentIndex + 1}/${quizItems.size}",
            style = MaterialTheme.typography.titleMedium
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = if (currentQuiz.type == QuizType.OX) "O/X 문제" else "단답형 문제",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(text = currentQuiz.question, style = MaterialTheme.typography.bodyLarge)
            }
        }

        if (currentQuiz.type == QuizType.OX) {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                OutlinedButton(onClick = { userAnswer = "O" }) { Text("O") }
                OutlinedButton(onClick = { userAnswer = "X" }) { Text("X") }
            }
            Text(text = "선택: ${if (userAnswer.isBlank()) "없음" else userAnswer}")
        } else {
            OutlinedTextField(
                value = userAnswer,
                onValueChange = { userAnswer = it },
                modifier = Modifier.fillMaxWidth(),
                label = { Text("정답 입력") },
                singleLine = true
            )
        }

        Button(
            onClick = {
                val isCorrect = studyViewModel.checkAnswer(currentQuiz, userAnswer)
                resultText = if (isCorrect) {
                    "정답입니다! ${currentQuiz.explanation}"
                } else {
                    "오답입니다. 정답: ${currentQuiz.answer} | ${currentQuiz.explanation}"
                }
            },
            enabled = userAnswer.isNotBlank()
        ) {
            Text("정답 확인")
        }

        resultText?.let {
            Text(
                text = it,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Button(onClick = {
            currentIndex = (currentIndex + 1) % quizItems.size
            userAnswer = ""
            resultText = null
        }) {
            Text("다음 문제")
        }
    }
}
