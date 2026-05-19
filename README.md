<img width="1080" height="2340" alt="Leaderboard" src="https://github.com/user-attachments/assets/4000dcf2-b941-4ba3-ab94-325e8423ae15" />
# Real-time Leaderboard System

A production-grade Android application demonstrating a real-time leaderboard with a modular architecture.

## 🏗 Architecture
The project follows **Clean Architecture** with **MVVM** and is split into 4 modules:
- **:app**: Entry point and Navigation.
- **:leaderboard**: UI Layer (Composables, ViewModels, UseCases).
- **:score-engine**: Pure Kotlin module simulating real-time score generation (UI-agnostic).
- **:core**: Shared components, Dagger Hilt DI, and Domain Utilities.

## 🚀 Key Features
- **Real-time Updates**: Powered by Kotlin Coroutines and Flows.
- **Competition Ranking**: Implements "1224" ranking (ties get same rank, next rank skipped).
- **Smooth UI**: Uses Compose `animateItem` for rank transitions and `AnimatedContent` for score updates.
- **UI-Agnostic Engine**: The score generator is isolated from the UI, making it highly testable and reusable.

## 🧠 Leadership & Decisions
- **Module Splitting**: Done to ensure separation of concerns. The engine can be replaced by a WebSocket implementation without touching the UI.
- **Ranking Logic**: Placed in the Domain layer (`core` module) to keep ViewModels thin and logic centralized.
- **Performance**: Used `key` in LazyColumn to prevent unnecessary recompositions and flickering.

## 🛠 Tech Stack
- Kotlin, Coroutines, Flow
- Jetpack Compose
- Dagger Hilt (DI)
- Architecture Components (ViewModel, StateFlow)

## 🧪 Testing
- Unit tests included for `RankingCalculator` to ensure core business rules are always correct.
