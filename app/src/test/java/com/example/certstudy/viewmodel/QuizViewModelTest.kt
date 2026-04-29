package com.example.certstudy.viewmodel

import com.example.certstudy.data.IncorrectNoteRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@OptIn(ExperimentalCoroutinesApi::class)
class QuizViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: IncorrectNoteRepository

    @Before
    fun setUp() {
        repository = mockk()
        coEvery { repository.insertIncorrectQuiz(any()) } returns Unit
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun onOptionSelected_whenCorrectAnswerSelected_scoreIncreasesAndEvaluatedTrue() = runTest {
        val viewModel = QuizViewModel(repository)
        val currentQuiz = viewModel.getQuiz(0)

        viewModel.onOptionSelected(currentQuiz.correctIndex)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.score)
        assertTrue(uiState.isEvaluated)
    }

    @Test
    fun onOptionSelected_whenWrongAnswerSelected_scoreStaysAndWrongIndexSaved() = runTest {
        val viewModel = QuizViewModel(repository)
        val currentQuiz = viewModel.getQuiz(0)
        val wrongIndex = (0..3).first { it != currentQuiz.correctIndex }

        viewModel.onOptionSelected(wrongIndex)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(0, uiState.score)
        assertEquals(wrongIndex, uiState.selectedOptionIndex)
    }

    @Test
    fun onOptionSelected_whenWrongAnswerSelected_insertIncorrectQuizCalledOnce() = runTest {
        val viewModel = QuizViewModel(repository)
        val currentQuiz = viewModel.getQuiz(0)
        val wrongIndex = (0..3).first { it != currentQuiz.correctIndex }

        viewModel.onOptionSelected(wrongIndex)
        advanceUntilIdle()

        coVerify(exactly = 1) { repository.insertIncorrectQuiz(any()) }
    }

    @Test
    fun onOptionSelected_whenAlreadyEvaluated_scoreAndSelectionDoNotChange() = runTest {
        val viewModel = QuizViewModel(repository)
        val currentQuiz = viewModel.getQuiz(0)
        val wrongIndex = (0..3).first { it != currentQuiz.correctIndex }

        // 오답을 클릭해서 채점 완료 상태로 변경
        viewModel.onOptionSelected(wrongIndex)
        advanceUntilIdle()

        var uiState = viewModel.uiState.value
        assertTrue(uiState.isEvaluated)
        val initialScore = uiState.score
        val initialSelectedOptionIndex = uiState.selectedOptionIndex

        // 이미 채점 완료된 상태에서 정답 인덱스를 다시 클릭
        viewModel.onOptionSelected(currentQuiz.correctIndex)
        advanceUntilIdle()

        // 점수와 선택된 인덱스가 변하지 않고 그대로 유지되는지 검증
        uiState = viewModel.uiState.value
        assertEquals(initialScore, uiState.score)
        assertEquals(initialSelectedOptionIndex, uiState.selectedOptionIndex)
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MainDispatcherRule(
    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
) : TestWatcher() {
    override fun starting(description: Description) {
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description) {
        Dispatchers.resetMain()
    }
}
