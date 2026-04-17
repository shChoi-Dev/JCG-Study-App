package com.example.certstudy.data

import com.example.certstudy.model.Keyword
import com.example.certstudy.model.Quiz

object MockData {
    val coreKeywords: List<Keyword> = listOf(
        Keyword(
            title = "결합도 (Coupling)",
            category = "소프트웨어 공학",
            description = "모듈 간 의존도는 낮을수록 좋으며, 유지보수성과 변경 용이성이 높아집니다."
        ),
        Keyword(
            title = "응집도 (Cohesion)",
            category = "소프트웨어 공학",
            description = "모듈 내부 요소가 하나의 책임에 집중될수록 응집도가 높고 설계 품질이 좋아집니다."
        ),
        Keyword(
            title = "트랜잭션 ACID",
            category = "데이터베이스",
            description = "원자성, 일관성, 고립성, 지속성은 안전한 데이터 처리를 위한 핵심 성질입니다."
        ),
        Keyword(
            title = "정규화",
            category = "데이터베이스",
            description = "데이터 중복을 줄이고 이상 현상을 방지하기 위해 테이블을 구조적으로 분해합니다."
        ),
        Keyword(
            title = "인덱스 (Index)",
            category = "데이터베이스",
            description = "조회 성능을 높이는 자료구조지만, 삽입/수정/삭제 시 추가 비용이 발생할 수 있습니다."
        ),
        Keyword(
            title = "OSI 7계층",
            category = "네트워크",
            description = "네트워크 통신을 7개 계층으로 분리해 역할을 나누고 문제 분석을 쉽게 합니다."
        ),
        Keyword(
            title = "TCP/UDP",
            category = "네트워크",
            description = "TCP는 신뢰성 중심, UDP는 속도 중심 전송 방식으로 목적에 따라 선택합니다."
        ),
        Keyword(
            title = "OCP",
            category = "객체지향 설계",
            description = "확장에는 열려 있고 변경에는 닫혀 있어야 하며, 변경 없이 기능 확장을 지향합니다."
        ),
        Keyword(
            title = "블랙박스 테스트",
            category = "소프트웨어 테스트",
            description = "내부 구현을 보지 않고 요구사항 기반으로 입력/출력 중심 테스트를 수행합니다."
        ),
        Keyword(
            title = "스크럼",
            category = "애자일",
            description = "스프린트 반복과 데일리 스탠드업으로 빠른 피드백과 점진적 개선을 수행합니다."
        )
    )

    val quizzes: List<Quiz> = listOf(
        Quiz(
            question = "좋은 소프트웨어 설계를 위한 모듈의 독립성 방향으로 옳은 것은?",
            options = listOf(
                "결합도 낮게, 응집도 낮게",
                "결합도 높게, 응집도 높게",
                "결합도 낮게, 응집도 높게",
                "결합도 높게, 응집도 낮게"
            ),
            correctIndex = 2,
            explanation = "모듈의 독립성을 높이려면 모듈 간의 의존성인 결합도(Coupling)는 약하게, 모듈 내부의 기능적 집중도인 응집도(Cohesion)는 강하게 설계해야 합니다."
        ),
        Quiz(
            question = "객체 지향 설계 원칙 중, '소프트웨어 개체는 확장에는 열려 있어야 하고, 변경에는 닫혀 있어야 한다'는 원칙은?",
            options = listOf(
                "SRP (단일 책임 원칙)",
                "OCP (개방-폐쇄 원칙)",
                "LSP (리스코프 치환 원칙)",
                "DIP (의존 역전 원칙)"
            ),
            correctIndex = 1,
            explanation = "OCP(Open-Closed Principle)는 기존 코드를 변경하지 않고도 기능을 추가할 수 있도록 설계해야 한다는 원칙입니다."
        ),
        Quiz(
            question = "데이터베이스 정규화 과정 중, 이행적 함수 종속(A->B, B->C 일 때 A->C)을 제거하는 정규형은?",
            options = listOf(
                "제1정규형(1NF)",
                "제2정규형(2NF)",
                "제3정규형(3NF)",
                "BCNF"
            ),
            correctIndex = 2,
            explanation = "제3정규형(3NF)은 기본키가 아닌 일반 속성들 간의 함수적 종속성인 '이행적 함수 종속'을 제거하는 단계입니다."
        ),
        Quiz(
            question = "OSI 7계층 중 종단 간(End-to-End) 신뢰성 있는 데이터 전달을 담당하며, TCP와 UDP 프로토콜이 동작하는 계층은?",
            options = listOf(
                "네트워크 계층",
                "전송 계층",
                "세션 계층",
                "응용 계층"
            ),
            correctIndex = 1,
            explanation = "전송 계층(Transport Layer)은 포트 번호를 사용하여 도착지 프로세스까지 데이터를 신뢰성 있게 전달합니다."
        ),
        Quiz(
            question = "블랙박스 테스트 기법 중, 입력 데이터의 영역을 할당하고 각 영역의 유효값과 무효값을 그룹핑하여 테스트 케이스를 도출하는 기법은?",
            options = listOf(
                "경계값 분석",
                "동치 분할 검사",
                "원인-효과 그래프",
                "기본 경로 테스트"
            ),
            correctIndex = 1,
            explanation = "동치 분할 검사(Equivalence Partitioning)는 타당한 입력 자료와 타당하지 않은 입력 자료의 개수를 균등하게 하여 검사하는 기법입니다."
        ),
        Quiz(
            question = "UML 다이어그램 중 시스템의 정적인 구조를 표현하며, 속성과 연산으로 구성된 객체 타입을 정의하는 다이어그램은?",
            options = listOf(
                "유스케이스 다이어그램",
                "시퀀스 다이어그램",
                "클래스 다이어그램",
                "활동 다이어그램"
            ),
            correctIndex = 2,
            explanation = "클래스 다이어그램은 시스템을 구성하는 클래스들과 그들 간의 관계를 보여주는 대표적인 정적(구조적) 다이어그램입니다."
        ),
        Quiz(
            question = "HTTP 상태 코드 중, 클라이언트가 권한이 없거나 인증되지 않아 요청이 거부되었음을 의미하는 코드는?",
            options = listOf(
                "200 OK",
                "401 Unauthorized",
                "404 Not Found",
                "500 Internal Server Error"
            ),
            correctIndex = 1,
            explanation = "401 Unauthorized는 클라이언트가 해당 리소스에 대한 인증 정보를 가지고 있지 않을 때 반환되는 에러 코드입니다."
        ),
        Quiz(
            question = "암호화 알고리즘 중, 암호화 키와 복호화 키가 서로 다른 비대칭키(공개키) 암호화 방식은?",
            options = listOf(
                "DES",
                "AES",
                "SEED",
                "RSA"
            ),
            correctIndex = 3,
            explanation = "RSA는 소인수분해의 어려움을 이용한 대표적인 공개키(비대칭키) 암호화 알고리즘입니다. 나머지는 모두 대칭키 알고리즘입니다."
        ),
        Quiz(
            question = "Linux/Unix 파일 접근 권한에서 'rwxr-x--x'가 의미하는 8진수 값은?",
            options = listOf(
                "751",
                "755",
                "771",
                "711"
            ),
            correctIndex = 0,
            explanation = "r(읽기)=4, w(쓰기)=2, x(실행)=1 입니다. 소유자(rwx=7), 그룹(r-x=5), 기타(--x=1) 이므로 751이 됩니다."
        ),
        Quiz(
            question = "애자일(Agile) 개발 방법론 중, 매일 15분 정도의 짧은 스탠드업 미팅을 가지며 스프린트 단위로 개발을 진행하는 방식은?",
            options = listOf(
                "폭포수 모델",
                "스크럼 (Scrum)",
                "익스트림 프로그래밍 (XP)",
                "칸반 (Kanban)"
            ),
            correctIndex = 1,
            explanation = "스크럼(Scrum)은 짧은 개발 기간(스프린트)을 반복하며, 매일 스크럼 미팅을 통해 프로젝트 진행 상태를 공유하는 애자일 방법론입니다."
        )
    )
}

