package com.example.certstudy.viewmodel

import androidx.lifecycle.ViewModel
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
            keywords = listOf(
                Keyword(
                    title = "트랜잭션 ACID",
                    category = "데이터베이스",
                    description = "원자성, 일관성, 고립성, 지속성의 특징과 예시를 정리하세요."
                ),
                Keyword(
                    title = "정규화 단계",
                    category = "데이터베이스 설계",
                    description = "제1정규형부터 제3정규형까지의 차이와 목적을 이해합니다."
                ),
                Keyword(
                    title = "교착상태(Deadlock)",
                    category = "운영체제",
                    description = "발생 조건 네 가지와 예방/회피 방법을 정리합니다."
                ),
                Keyword(
                    title = "프로토콜 계층",
                    category = "네트워크",
                    description = "OSI 7계층과 각 계층의 대표 장비/프로토콜을 암기합니다."
                ),
                Keyword(
                    title = "자료구조 시간복잡도",
                    category = "자료구조",
                    description = "배열, 리스트, 스택/큐, 해시테이블의 평균/최악 시간복잡도를 비교합니다."
                )
            )
        )
    )
    val uiState: StateFlow<MainUiState> = _uiState.asStateFlow()
}

