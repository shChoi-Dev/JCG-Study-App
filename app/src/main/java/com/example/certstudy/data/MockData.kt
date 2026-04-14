package com.example.certstudy.data

import com.example.certstudy.model.QuizItem
import com.example.certstudy.model.QuizType
import com.example.certstudy.model.StudyKeyword

object MockData {
    val summaryJsonStyle: List<StudyKeyword> = listOf(
        StudyKeyword(
            title = "Singleton Pattern",
            category = "디자인 패턴",
            summary = "객체 인스턴스를 하나만 생성하고 전역에서 접근하도록 보장한다."
        ),
        StudyKeyword(
            title = "Factory Method",
            category = "디자인 패턴",
            summary = "객체 생성을 서브클래스에 위임해 결합도를 낮춘다."
        ),
        StudyKeyword(
            title = "OSI 7계층",
            category = "네트워크",
            summary = "물리-데이터링크-네트워크-전송-세션-표현-응용 계층으로 구성된다."
        ),
        StudyKeyword(
            title = "정규화",
            category = "DB",
            summary = "데이터 중복을 최소화하고 무결성을 높이기 위한 테이블 분해 과정이다."
        ),
        StudyKeyword(
            title = "트랜잭션 ACID",
            category = "DB",
            summary = "원자성, 일관성, 격리성, 지속성을 만족해야 안전한 데이터 처리가 가능하다."
        )
    )

    val quizJsonStyle: List<QuizItem> = listOf(
        QuizItem(
            id = 1,
            type = QuizType.OX,
            question = "OSI 7계층에서 전송 계층은 종단 간 신뢰성 있는 데이터 전송을 담당한다.",
            answer = "O",
            explanation = "전송 계층(TCP/UDP)은 신뢰성과 흐름 제어를 담당한다."
        ),
        QuizItem(
            id = 2,
            type = QuizType.SHORT_ANSWER,
            question = "데이터베이스 트랜잭션의 4가지 성질을 나타내는 약어는?",
            answer = "ACID",
            explanation = "Atomicity, Consistency, Isolation, Durability"
        ),
        QuizItem(
            id = 3,
            type = QuizType.OX,
            question = "Singleton 패턴은 객체를 필요할 때마다 새롭게 생성하는 패턴이다.",
            answer = "X",
            explanation = "Singleton은 하나의 인스턴스만 생성하는 패턴이다."
        ),
        QuizItem(
            id = 4,
            type = QuizType.SHORT_ANSWER,
            question = "OSI 7계층 중 IP 프로토콜이 동작하는 계층은?",
            answer = "네트워크",
            explanation = "IP는 네트워크 계층의 핵심 프로토콜이다."
        )
    )
}

