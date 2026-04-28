package com.example.certstudy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.certstudy.data.IncorrectNoteRepository
import com.example.certstudy.data.IncorrectQuiz
import kotlinx.coroutines.launch

class IncorrectNoteViewModel(private val repository: IncorrectNoteRepository) : ViewModel() {
    val incorrectQuizzes = repository.getAllIncorrectQuizzes()

    fun delete(incorrectQuiz: IncorrectQuiz) {
        viewModelScope.launch {
            repository.deleteIncorrectQuiz(incorrectQuiz)
        }
    }
}
