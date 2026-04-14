package com.example.certstudy.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.certstudy.viewmodel.StudyViewModel
import java.time.LocalDate
import java.time.format.DateTimeParseException

@Composable
fun ExamDateSettingScreen(
    studyViewModel: StudyViewModel,
    onBack: () -> Unit
) {
    val uiState by studyViewModel.homeUiState.collectAsStateWithLifecycle()
    var dateInput by remember(uiState.examDate) { mutableStateOf(uiState.examDate.toString()) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(text = "시험일 설정", style = MaterialTheme.typography.headlineSmall)

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    text = "YYYY-MM-DD 형식으로 입력하세요. 예: 2026-08-23",
                    style = MaterialTheme.typography.bodyMedium
                )

                OutlinedTextField(
                    value = dateInput,
                    onValueChange = {
                        dateInput = it
                        errorMessage = null
                    },
                    label = { Text("시험일") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )

                errorMessage?.let {
                    Text(text = it, color = MaterialTheme.colorScheme.error)
                }

                Button(
                    onClick = {
                        try {
                            val parsed = LocalDate.parse(dateInput)
                            studyViewModel.updateExamDate(parsed)
                            onBack()
                        } catch (_: DateTimeParseException) {
                            errorMessage = "날짜 형식이 올바르지 않습니다. YYYY-MM-DD 형식으로 입력해주세요."
                        }
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("저장")
                }
            }
        }
    }
}
