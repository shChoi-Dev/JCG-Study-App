package com.example.certstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.certstudy.data.StudyPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.ChronoUnit

data class HomeUiState(
    val examDate: LocalDate = LocalDate.of(2026, 8, 23),
    val dDay: Long = 0
)

class StudyViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = StudyPreferencesRepository(application.applicationContext)

    private val _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState: StateFlow<HomeUiState> = _homeUiState.asStateFlow()

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

}
