package com.example.certstudy.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.certstudy.model.Quiz

@Entity(
    tableName = "incorrect_quiz",
    indices = [Index(value = ["quizKey"], unique = true)]
)
data class IncorrectQuiz(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val quizKey: String,
    val question: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val option4: String,
    val correctIndex: Int,
    val selectedOptionIndex: Int,
    val explanation: String
)

fun Quiz.toIncorrectQuiz(selectedOptionIndex: Int): IncorrectQuiz {
    val normalizedOptions = options.joinToString("|")
    return IncorrectQuiz(
        quizKey = "$question|$normalizedOptions|$correctIndex",
        question = question,
        option1 = options[0],
        option2 = options[1],
        option3 = options[2],
        option4 = options[3],
        correctIndex = correctIndex,
        selectedOptionIndex = selectedOptionIndex,
        explanation = explanation
    )
}
