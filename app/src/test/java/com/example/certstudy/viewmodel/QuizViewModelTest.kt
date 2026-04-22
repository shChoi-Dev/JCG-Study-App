package com.example.certstudy.viewmodel

import android.app.Application
import com.example.certstudy.data.AppDatabase
import com.example.certstudy.data.IncorrectQuizDao
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkObject
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

    private lateinit var application: Application
    private lateinit var appDatabase: AppDatabase
    private lateinit var incorrectQuizDao: IncorrectQuizDao

    @Before
    fun setUp() {
        application = mockk(relaxed = true)
        appDatabase = mockk()
        incorrectQuizDao = mockk()

        coEvery { incorrectQuizDao.insert(any()) } returns Unit
        every { appDatabase.incorrectQuizDao() } returns incorrectQuizDao

        mockkObject(AppDatabase.Companion)
        every { AppDatabase.getDatabase(application) } returns appDatabase
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun onOptionSelected_whenCorrectAnswerSelected_scoreIncreasesAndEvaluatedTrue() = runTest {
        val viewModel = QuizViewModel(application)
        val currentQuiz = viewModel.getQuiz(0)

        viewModel.onOptionSelected(currentQuiz.correctIndex)
        advanceUntilIdle()

        val uiState = viewModel.uiState.value
        assertEquals(1, uiState.score)
        assertTrue(uiState.isEvaluated)
    }

    @Test
    fun onOptionSelected_whenWrongAnswerSelected_scoreStaysAndWrongIndexSaved() = runTest {
        val viewModel = QuizViewModel(application)
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
        val viewModel = QuizViewModel(application)
        val currentQuiz = viewModel.getQuiz(0)
        val wrongIndex = (0..3).first { it != currentQuiz.correctIndex }

        viewModel.onOptionSelected(wrongIndex)
        advanceUntilIdle()

        coVerify(exactly = 1) { incorrectQuizDao.insert(any()) }
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
