# 📝 정처기 스마트 스터디 (CertStudy)

정보처리기사 수험생을 위한 **Jetpack Compose 기반 모바일 퀴즈 & 오답노트 애플리케이션**입니다. 
최신 안드로이드 권장 아키텍처(MVVM)를 따르며, 사용자 맞춤형 학습 경험을 제공하기 위해 로컬 DB(Room)와 코루틴(Coroutines) 기반의 비동기 처리를 적극 활용했습니다.

> **💡 Development Point (AI-Assisted "Vibe Coding")**

> Cursor, Claude 등 최신 LLM 툴을 적극 활용하여 UI/UX 프로토타이핑과 보일러플레이트 코드 작성에 드는 시간을 획기적으로 단축했습니다. 
이를 통해 확보한 시간은 **상태 관리(StateFlow) 최적화, Room DB 설계, 그리고 비즈니스 로직의 단위 테스트(Unit Test)를 구축하는 등 핵심 엔지니어링 품질을 높이는 데 집중**했습니다.

---

## 🛠 Tech Stack

* **Language:** Kotlin
* **UI Toolkit:** Jetpack Compose, Material3
* **Architecture:** MVVM (Model-View-ViewModel), UDF (Unidirectional Data Flow)
* **Asynchronous / Reactive:** Coroutines, StateFlow
* **Local Storage:** Room Database (SQLite), Preferences DataStore
* **Testing:** JUnit4, MockK, Coroutines Test
* **Navigation:** Navigation Compose

---

## ✨ Key Features

### 1. 동적 퀴즈 시스템 (State Management)
* 매 실행 시 전체 문제 은행에서 5문제를 무작위 추출(Shuffle)하여 출제.
* `StateFlow`를 활용해 퀴즈 진행 상태(현재 문항, 점수, 채점 여부 등)를 실시간으로 UI에 반영.
* 정답/오답에 따른 즉각적인 색상 피드백 및 해설 제공, 부드러운 화면 전환 애니메이션(AnimatedContent) 적용.

### 2. 자동화된 스마트 오답 노트 (Room DB)
* 사용자가 틀린 문제와 선택한 오답 데이터를 Room DB에 영구 저장.
* `OnConflictStrategy.REPLACE` 및 쿼리 최적화를 통해 동일한 문제의 중복 저장을 방지.
* 사용자가 고른 오답과 실제 정답을 직관적으로 비교할 수 있는 맞춤형 해설 UI 제공.

### 3. 학습 대시보드 (DataStore)
* Preferences DataStore를 활용한 시험일(D-Day) 설정 및 영구 저장.
* 무작위 핵심 키워드 노출을 통한 데일리 복습 기능.

---

## 🏗 Architecture & Design Patterns

* **MVVM 아키텍처 적용:** UI(Compose)와 비즈니스 로직(ViewModel)을 완벽히 분리하여 결합도를 낮추고 유지보수성을 높였습니다.
* **단방향 데이터 흐름 (UDF):** ViewModel에서 노출하는 읽기 전용 `StateFlow`를 통해서만 UI가 업데이트되며, 사용자 액션은 ViewModel의 명시적인 함수 호출로만 상태를 변경하도록 통제했습니다.
* **싱글톤 (Singleton) 패턴:** Room Database 인스턴스 생성 시 `companion object`와 `synchronized` 블록을 사용하여 애플리케이션 전역에서 단 하나의 DB 커넥션만 유지되도록 설계했습니다.

---

## 🚨 Trouble Shooting & Problem Solving

**1. 상태 변경 타이밍의 엣지 케이스(Edge Case) 방어 로직 구현**
* **Issue:** 퀴즈 화면에서 사용자가 오답을 선택해 채점이 완료된 직후, 고의로 정답 버튼을 연속 클릭할 경우 점수(`score`)가 비정상적으로 증가하는 헛점이 발견됨.
* **Resolution:** UI 단의 임시 처방이 아닌, ViewModel 비즈니스 로직 단에서 `isEvaluated` 상태를 검증하는 근본적인 방어 코드를 추가함. 이를 통해 한 번 평가가 완료된 문제는 어떤 추가 입력에도 상태가 변이되지 않음을 보장함.

**2. 저사양 빌드 환경에서의 JVM OutOfMemoryError 해결**
* **Issue:** 안드로이드 최신 빌드 도구 적용 후, 제한된 하드웨어 리소스(Max Heap 512MB)로 인해 리소스 링킹 및 덱싱(Dexing) 과정에서 지속적인 OOM 발생.
* **Resolution:** 일시적으로 메모리 옵션만 수정하는 대신, 현재 개발 환경과 호환성이 가장 안정적인 AGP 8.x 및 Kotlin 1.9.x 버전으로 프로젝트 구조를 안전하게 다운그레이드. `gradle.properties`의 JVM 인자 튜닝과 백그라운드 데몬 정리를 통해 안정적인 빌드 파이프라인을 구축함.

---

## ✅ Testing & QA

비즈니스 로직의 무결성을 증명하기 위해 **MockK**를 활용한 단위 테스트(Unit Test)를 작성 및 통과했습니다.

* `QuizViewModel`의 정답/오답 선택 시의 `score`, `selectedOptionIndex` 상태 전이 검증 완료.
* 오답 선택 시 Repository(Room DB Dao)의 `insert()` 메서드가 정확히 1회 호출되는지 모킹(`coVerify`) 검증 완료.
* 이미 채점된 문항 재클릭 시 상태 불변성을 검증하는 회귀(Regression) 방지 테스트 구축.