package com.example.certstudy.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.certstudy.data.AppDatabase
import com.example.certstudy.data.IncorrectQuiz
import kotlinx.coroutines.launch

class IncorrectNoteViewModel(application: Application) : AndroidViewModel(application) {
    private val incorrectQuizDao = AppDatabase.getDatabase(application).incorrectQuizDao()
    val incorrectQuizzes = incorrectQuizDao.getAll()

    fun delete(incorrectQuiz: IncorrectQuiz) {
        viewModelScope.launch {
            incorrectQuizDao.delete(incorrectQuiz)
        }
    }
}
