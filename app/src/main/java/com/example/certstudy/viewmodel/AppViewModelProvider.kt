package com.example.certstudy.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.certstudy.data.AppDatabase
import com.example.certstudy.data.IncorrectNoteRepository

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val application = (this[AndroidViewModelFactory.APPLICATION_KEY] as Application)
            val database = AppDatabase.getDatabase(application)
            val repository = IncorrectNoteRepository(database.incorrectQuizDao())
            QuizViewModel(repository)
        }
        initializer {
            val application = (this[AndroidViewModelFactory.APPLICATION_KEY] as Application)
            val database = AppDatabase.getDatabase(application)
            val repository = IncorrectNoteRepository(database.incorrectQuizDao())
            IncorrectNoteViewModel(repository)
        }
    }
}
