package com.example.certstudy.model

data class StudyKeyword(
    val title: String,
    val category: String,
    val summary: String
)

enum class QuizType {
    OX,
    SHORT_ANSWER
}

data class QuizItem(
    val id: Int,
    val type: QuizType,
    val question: String,
    val answer: String,
    val explanation: String
)

