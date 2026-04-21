package com.example.certstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.certstudy.data.AppDatabase
import com.example.certstudy.data.MockData
import com.example.certstudy.data.toIncorrectQuiz
import com.example.certstudy.model.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class QuizUiState(
    val currentIndex: Int = 0,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val selectedOptionIndex: Int? = null,
    val isEvaluated: Boolean = false
)

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private var quizzes: List<Quiz> = emptyList()
    private val incorrectQuizDao = AppDatabase.getDatabase(application).incorrectQuizDao()

    val quizCount: Int
        get() = quizzes.size

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    init {
        startNewQuizSession()
    }

    private fun startNewQuizSession() {
        quizzes = MockData.quizzes.shuffled().take(5)
        _uiState.value = QuizUiState()
    }

    fun onOptionSelected(optionIndex: Int) {
        val state = _uiState.value
        if (state.isEvaluated || state.isFinished) return
        if (optionIndex !in 0..3) return

        val quiz = quizzes[state.currentIndex]
        val isCorrect = optionIndex == quiz.correctIndex
        if (!isCorrect) {
            viewModelScope.launch {
                incorrectQuizDao.insert(quiz.toIncorrectQuiz(selectedOptionIndex = optionIndex))
            }
        }

        val newScore = if (isCorrect) state.score + 1 else state.score
        _uiState.update {
            it.copy(
                score = newScore,
                selectedOptionIndex = optionIndex,
                isEvaluated = true
            )
        }
    }

    fun moveToNextQuestion() {
        val state = _uiState.value
        if (!state.isEvaluated || state.isFinished) return

        val nextIndex = state.currentIndex + 1
        if (nextIndex >= quizzes.size) {
            _uiState.update { it.copy(isFinished = true) }
        } else {
            _uiState.update {
                it.copy(
                    currentIndex = nextIndex,
                    selectedOptionIndex = null,
                    isEvaluated = false
                )
            }
        }
    }

    fun getQuiz(index: Int): Quiz = quizzes[index]

    fun restart() {
        startNewQuizSession()
    }
}

