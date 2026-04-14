package com.example.certstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.certstudy.data.MockData
import com.example.certstudy.data.StudyPreferencesRepository
import com.example.certstudy.model.QuizItem
import com.example.certstudy.model.QuizType
import com.example.certstudy.model.StudyKeyword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class HomeUiState(
    val examDate: LocalDate = LocalDate.of(2026, 8, 23),
    val dDay: Long = 0,
    val keywords: List<StudyKeyword> = emptyList()
)

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StudyPreferencesRepository(application.applicationContext)

    private val _homeUiState = MutableStateFlow(
        HomeUiState(keywords = MockData.summaryJsonStyle.take(3))
    )
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    val quizzes: List<QuizItem> = MockData.quizJsonStyle

    init {
        viewModelScope.launch {
            repository.examDateEpochDayFlow.collect { savedEpoch ->
                val examDate = savedEpoch?.let { LocalDate.ofEpochDay(it) } ?: LocalDate.of(2026, 8, 23)
                _homeUiState.update {
                    it.copy(
                        examDate = examDate,
                        dDay = ChronoUnit.DAYS.between(LocalDate.now(), examDate)
                    )
                }
            }
        }
    }

    fun updateExamDate(examDate: LocalDate) {
        viewModelScope.launch {
            repository.saveExamDateEpochDay(examDate.toEpochDay())
        }
    }

    fun checkAnswer(quiz: QuizItem, userInput: String): Boolean {
        return when (quiz.type) {
            QuizType.OX -> userInput.trim().uppercase() == quiz.answer.uppercase()
            QuizType.SHORT_ANSWER -> userInput.trim().equals(quiz.answer.trim(), ignoreCase = true)
        }
    }
}
