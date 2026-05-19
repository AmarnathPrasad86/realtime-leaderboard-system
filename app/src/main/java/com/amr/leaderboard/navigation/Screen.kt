package com.amr.leaderboard.navigation

/**
 * Sealed class representing the different screens in the application.
 * Using data objects for routes is the modern Kotlin practice for navigation.
 */
sealed class Screen(val route: String) {
    data object Leaderboard : Screen("leaderboard")
}
