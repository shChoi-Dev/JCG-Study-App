package com.example.certstudy.viewmodel

import androidx.lifecycle.ViewModel
import com.example.certstudy.data.MockData
import com.example.certstudy.model.Keyword
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class MainUiState(
    val keywords: List<Keyword> = emptyList()
)

class MainViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        MainUiState(
            keywords = MockData.coreKeywords
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
}

