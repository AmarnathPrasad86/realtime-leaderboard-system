# Code Review Simulation (Lead Perspective)

This document simulates a code review for a system implemented by a mid-level developer.

### 1. [Must Fix] Score Calculation Logic
- **Location:** `LeaderboardRepositoryImpl.kt`
- **Comment:** "We are currently using `sumOf { it.points }`. If the Score Engine sends cumulative totals rather than deltas, this will exponentially inflate scores. We should switch to taking the latest score per user based on the timestamp."
- **Reasoning:** Ensures data integrity and prevents 'ghost' points.

### 2. [Must Fix] UI Performance & Stability
- **Location:** `LeaderboardScreen.kt` (LazyColumn)
- **Comment:** "The `items` call is missing a `key`. Without a stable key (like `userId`), Compose cannot track items during reordering, leading to list flickering and poor animation performance."
- **Reasoning:** Essential for smooth rank movement animations.

### 3. [Improvement] Resource Management
- **Location:** `LeaderboardRepositoryImpl.kt` (stateIn)
- **Comment:** "Change `SharingStarted.Eagerly` to `SharingStarted.WhileSubscribed(5000)`. This ensures the Score Engine stops generating data when the app is in the background."
- **Reasoning:** Saves battery and CPU cycles.

### 4. [Improvement] Injectable Dispatchers
- **Location:** `FakeGameEngine.kt`
- **Comment:** "Avoid using `Dispatchers.Default` directly. Inject a `DispatcherProvider` instead."
- **Reasoning:** Hardcoded dispatchers make unit testing much harder.

### 5. [Tech Debt] Hardcoded UI Strings
- **Location:** `LeaderboardScreen.kt`
- **Comment:** "Strings like 'Realtime Leaderboard' are hardcoded. Move these to `strings.xml`."
- **Reasoning:** Improves maintainability and prepares the app for Localization (i18n).

### 6. [Tech Debt] Lack of Error Handling
- **Location:** `LeaderboardState.kt`
- **Comment:** "The UI state has no 'Error' field. We need to handle cases where the engine fails or the data stream is interrupted."
- **Reasoning:** Production-ready apps must fail gracefully.

### 7. [Improvement] State Hoisting
- **Location:** `LeaderboardItem.kt`
- **Comment:** "The 'isUpdating' animation logic is inside the item. Consider hoisting this or using a more centralized trigger if items are recycled frequently."
- **Reasoning:** Keeps individual components lean.

### 8. [Tech Debt] Constant Management
- **Location:** `FakeGameEngine.kt`
- **Comment:** "Magic numbers like `500L` and `2000L` for delays should be moved to a `Constants` object."
- **Reasoning:** Centralized configuration makes it easier to tweak the simulation speed.
