package com.example.certstudy.data

import kotlinx.coroutines.flow.Flow

class IncorrectNoteRepository(private val incorrectQuizDao: IncorrectQuizDao) {
    fun getAllIncorrectQuizzes(): Flow<List<IncorrectQuiz>> {
        return incorrectQuizDao.getAll()
    }

    suspend fun insertIncorrectQuiz(incorrectQuiz: IncorrectQuiz) {
        incorrectQuizDao.insert(incorrectQuiz)
    }

    suspend fun deleteIncorrectQuiz(incorrectQuiz: IncorrectQuiz) {
        incorrectQuizDao.delete(incorrectQuiz)
    }
}
