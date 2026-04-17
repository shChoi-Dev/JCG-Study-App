package com.example.certstudy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.certstudy.data.MockData
import com.example.certstudy.model.Keyword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class KeywordUiState(
    val keywords: List<Keyword> = emptyList()
)

class HomeViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(KeywordUiState())
    val uiState: StateFlow<KeywordUiState> = _uiState.asStateFlow()

    init {
        refreshKeywords()
    }

    fun refreshKeywords() {
        val randomKeywords = MockData.coreKeywords.shuffled().take(3)
        _uiState.update { it.copy(keywords = randomKeywords) }
    }
}
