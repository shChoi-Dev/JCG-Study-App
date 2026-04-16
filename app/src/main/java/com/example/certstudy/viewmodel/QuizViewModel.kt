package com.example.certstudy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.certstudy.model.Quiz
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class QuizUiState(
    val currentIndex: Int = 0,
    val score: Int = 0,
    val isFinished: Boolean = false,
    val selectedOptionIndex: Int? = null,
    val isEvaluated: Boolean = false
)

class QuizViewModel : ViewModel() {

    private val quizzes = listOf(
        Quiz(
            question = "Singleton 패턴의 설명으로 옳은 것은?",
            options = listOf(
                "전역에서 단 하나의 인스턴스만 사용한다.",
                "호출될 때마다 새로운 객체를 생성한다.",
                "객체 생성을 서브클래스에 위임한다.",
                "알고리즘을 캡슐화해 런타임에 교체한다."
            ),
            correctIndex = 0,
            explanation = "Singleton은 애플리케이션 전역에서 단 하나의 인스턴스만을 보장하는 패턴이다."
        ),
        Quiz(
            question = "OSI 7계층에서 IP가 동작하는 계층은?",
            options = listOf(
                "데이터링크 계층",
                "네트워크 계층",
                "전송 계층",
                "응용 계층"
            ),
            correctIndex = 1,
            explanation = "IP 프로토콜은 네트워크 계층에서 주소 지정 및 라우팅을 담당한다."
        ),
        Quiz(
            question = "트랜잭션 ACID 중 '원자성(Atomicity)'의 의미로 옳은 것은?",
            options = listOf(
                "트랜잭션이 모두 성공해야만 결과가 반영된다.",
                "트랜잭션 수행 중에도 데이터가 항상 일관된 상태를 유지한다.",
                "트랜잭션이 끝나면 변경 내용이 영구적으로 반영된다.",
                "트랜잭션들이 서로 영향을 주지 않도록 격리한다."
            ),
            correctIndex = 0,
            explanation = "원자성은 트랜잭션이 전부 성공하거나 전부 실패하는 'all or nothing'을 의미한다."
        )
    )

    val quizCount: Int
        get() = quizzes.size

    private val _uiState = MutableStateFlow(QuizUiState())
    val uiState: StateFlow<QuizUiState> = _uiState.asStateFlow()

    fun onOptionSelected(optionIndex: Int) {
        val state = _uiState.value
        if (state.isEvaluated || state.isFinished) return
        if (optionIndex !in 0..3) return

        val quiz = quizzes[state.currentIndex]
        val isCorrect = optionIndex == quiz.correctIndex

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
        _uiState.value = QuizUiState()
    }
}

