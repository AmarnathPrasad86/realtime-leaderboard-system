# Realtime Leaderboard System

A scalable realtime gaming leaderboard system built using modern Android development practices, Clean Architecture, MVVM, Jetpack Compose, Kotlin Coroutines, Flow, and multi-module architecture.

---

# 🚀 Features

- Realtime leaderboard updates
- Random score generation engine
- Dynamic ranking system
- Score increase-only logic
- Multi-module clean architecture
- Reactive UI using StateFlow
- Dependency Injection using Hilt
- Modern Jetpack Compose UI
- Scalable and maintainable architecture

---

# 🏗️ Architecture

The project follows Clean Architecture principles with MVVM and modularization.

## Modules

### :app
Main application module responsible for app startup, navigation setup, dependency injection initialization, and connecting all feature modules together.

### :leaderboard
Contains the leaderboard presentation layer including:
- Jetpack Compose UI
- ViewModels
- UI State Management
- UseCases
- Leaderboard interactions
- Realtime ranking display

### :score-engine
Pure Kotlin business logic module responsible for:
- Realtime score simulation
- Random score generation
- Ranking algorithms
- Score processing logic
- Flow-based realtime data streaming

This module is completely UI-independent.

### :core
Shared foundational module containing:
- Dagger Hilt setup
- Shared utilities
- Base classes
- Common Compose components
- Extensions
- Coroutine dispatchers
- Shared models
- Domain utilities

---

# 🧠 Leadership & Engineering Decisions

## Why Multi-Module Architecture?
The application uses modularization to improve:
- Scalability
- Team collaboration
- Build performance
- Code maintainability
- Separation of concerns

## Why MVVM?
MVVM provides:
- Clear UI separation
- Better state management
- Easier testing
- Lifecycle-aware architecture

## Why StateFlow?
StateFlow was chosen for:
- Reactive UI updates
- Lifecycle awareness
- Efficient state handling
- Realtime leaderboard updates

## Why Clean Architecture?
Clean Architecture helps:
- Keep business logic independent
- Improve testability
- Reduce coupling
- Increase long-term maintainability

---

# 🛠️ Tech Stack

- Kotlin
- Jetpack Compose
- MVVM Architecture
- Clean Architecture
- Kotlin Coroutines
- StateFlow / Flow
- Dagger Hilt
- Multi-Module Architecture

---

# 📸 Screenshots

(Add screenshots here)

---

# 📦 Deliverables

✅ GitHub repository with complete source code  
✅ Architecture & Leadership documentation included

---

# ▶️ How to Run

1. Clone repository
2. Open in Android Studio
3. Sync Gradle
4. Run the app

---

# 👨‍💻 Author

Amar
