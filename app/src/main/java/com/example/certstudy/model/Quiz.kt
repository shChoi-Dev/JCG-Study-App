package com.example.certstudy.model

data class Quiz(
    val question: String,
    val options: List<String>,
    val correctIndex: Int,
    val explanation: String
) {
    init {
        require(options.size == 4) { "options must contain exactly 4 choices." }
        require(correctIndex in 0..3) { "correctIndex must be between 0 and 3." }
    }
}

